package com.sentia.android.base.vis.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.api.model.LoginResult
import com.sentia.android.base.vis.data.remote.RemoteDataSource
import com.sentia.android.base.vis.data.repository.BaseRepository
import com.sentia.android.base.vis.data.room.RoomInspectionDao
import com.sentia.android.base.vis.data.room.RoomInspectionDataSource
import com.sentia.android.base.vis.data.room.entity.Image
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.data.room.entity.InspectionImage
import com.sentia.android.base.vis.data.room.entity.UploadStatus
import com.sentia.android.base.vis.data.room.entity.UploadStatus.Status.*
import com.sentia.android.base.vis.util.Resource
import com.sentia.android.base.vis.util.Resource.Status.*
import com.sentia.android.base.vis.util.exception.AppException
import com.sentia.android.base.vis.util.forUi
import com.sentia.android.base.vis.util.orFalse
import com.sentia.android.base.vis.util.toLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.error
import org.jetbrains.anko.info


/**
 * Created by mariolopez on 27/12/17.
 */
class InspectionRepository : BaseRepository() {

    private val remoteDataSource by kodein.instance<RemoteDataSource>()
    private val roomVehicleDataSource by kodein.instance<RoomInspectionDataSource>()

    internal fun addInspections(inspections: List<Inspection>): LiveData<Resource<Nothing>> {
        val result = MutableLiveData<Resource<Nothing>>()
        result.value = Resource(LOADING)
        compositeDisposable += Single.fromCallable {
            val inspectionsInserted = roomVehicleDataSource.inspectionDao().insertAll(inspections)
            roomVehicleDataSource.inspectionDao().insertAllImages(inspections.flatMap { it.images })
//              insert inspection images
            roomVehicleDataSource.inspectionDao().insertAllInspectionImages(inspections.flatMap { inspection ->
                inspection.images.map { image -> InspectionImage(inspection.id, image.id) }
            })
            inspectionsInserted
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            result.value = Resource(SUCCESS)
                            info("DataSource has been updated with ${it.size} inspections")
                        },
                        onError = {
                            result.value = Resource(ERROR, exception = AppException(it))
                            error("DataSource hasn't been Populated yet cause of an error : " + it.message)
                        }
                )
        return result
    }

    fun doSearch(search: String?): LiveData<Resource<List<Inspection>>> {
        val result = MediatorLiveData<Resource<List<Inspection>>>()
        result.value = Resource(LOADING)
        result.addSource(roomVehicleDataSource.inspectionDao().findInspectionByVinOrRego(search.orEmpty()),
                { inspection: List<Inspection>? ->
                    result.value = Resource(SUCCESS, inspection)
                })
        return result
    }

    override fun findCompleteInspection(inspectionId: Long): Flowable<Resource<Inspection>> {
        val dao = roomVehicleDataSource.inspectionDao()

        return Observable.combineLatest(
                dao.findInspectionById(inspectionId).toObservable(),
                dao.getInspectionPhotos(inspectionId).toObservable(),

                BiFunction<Inspection?, List<Image>?, Inspection> { inspection: Inspection, photos: List<Image> ->
                    inspection.images.addAll(photos)
                    inspection
                })
                .map { Resource(SUCCESS, it) }
                .toFlowable(BackpressureStrategy.ERROR)

    }

    override fun getInspections(): LiveData<Resource<List<Inspection>>> {
        val result = MediatorLiveData<Resource<List<Inspection>>>()
        val remoteResult = MutableLiveData<Resource<List<Inspection>>>()

        remoteResult.value = Resource(LOADING)

        val dao = roomVehicleDataSource.inspectionDao()
        result.addSource(dao.getAllInspections().toLiveData(),
                { inspection: List<Inspection>? ->
                    result.value = Resource(SUCCESS, inspection)
                })
        //remote source
        result.addSource(remoteResult,
                { inspectionResult: Resource<List<Inspection>>? ->
                    result.value = inspectionResult
                })

        compositeDisposable += Observable.zip(
                dao.getAllInspections(NOT_SYNCED).toObservable(),
                remoteDataSource.getInspectionList().toObservable(),
                BiFunction { dbUnSyncedInspections: List<Inspection>?, remoteInspections: List<Inspection> ->

                    val dbUnSyncedIds = dbUnSyncedInspections?.map { it.id }
                    //we update just the Synced inspections
                    remoteInspections.filterNot { dbUnSyncedIds?.contains(it.id).orFalse() }
                })
                .forUi()
                .subscribeBy(
                        onNext = {
                            addInspections(it)//this will trigger the db source when completed
                        },
                        onError = {
                            error { it }
                            remoteResult.value = Resource(ERROR, null, AppException(it))
                        })
        return result
    }

    override fun getTotalInspections() = roomVehicleDataSource.inspectionDao().getTotalInspections()


    fun login(email: String, password: String): LiveData<Resource<LoginResult>> {
        val liveData = MutableLiveData<Resource<LoginResult>>()
        liveData.value = Resource(LOADING)

        compositeDisposable += remoteDataSource.login(email, password)
                .forUi()
                .subscribeBy(
                        onSuccess = { liveData.value = Resource(SUCCESS, it) },
                        onError = { liveData.value = Resource(ERROR, null, AppException(it)) })

        return liveData

    }

    fun sync(inspectionToUpload: Inspection?) {

        val dao = roomVehicleDataSource.inspectionDao()

        val singleInspectionOBs = Observable.just(if (inspectionToUpload == null) emptyList() else listOf(inspectionToUpload))
        val inspectionsToUploadObs = if (inspectionToUpload == null) dao.getAllInspections(NOT_SYNCED).toObservable() else singleInspectionOBs


        compositeDisposable += inspectionsToUploadObs
                .switchMap { Observable.fromIterable(it) }
                .flatMap { inspection ->
                    Observable.just(inspection)
                }
                .switchMap { findCompleteInspection(it.id).map { it.data }.first(it).toObservable() }
                .switchMap {
                    updateInspectionUploadStatus(it, UPLOADING) // it will update live data for inspections
                }
                .switchMap { remoteDataSource.uploadInspection(it) }
                .switchMap { updateInspectionUploadStatus(it, SYNCED) }
                .switchMap { updateStatusSingle(it, SYNCED, dao).concatWith(deleteSingle(it, dao)).toObservable() }
//                .onErrorResumeNext(Observable.just(it))
                .subscribeBy(
                        onNext = {
                            //todo delete the inspection
                            info { "Upload complete for inspection :${it.id} inspectedDate: " + it.id.toString() + " - " + it.uploadStatus.status.toString() }
                        },
                        onError = {
                            //todo grab the id for the inspection from the throwable and mark it as failed
                            error { it }
                        })
    }


    private fun updateInspectionUploadStatus(inspection: Inspection, uploadStatus: UploadStatus.Status): Observable<Inspection>? {
        val dao = roomVehicleDataSource.inspectionDao()
        return dao.findInspectionById(inspection.id)
                .firstOrError().toObservable()
                .switchMap {
                    updateStatusSingle(it, uploadStatus, dao).toObservable()
                }
                .switchMap { Observable.just(inspection) }
    }

    private fun updateStatusSingle(it: Inspection, uploadStatus: UploadStatus.Status, dao: RoomInspectionDao): Single<Inspection> {
        return Single.fromCallable {
            val updatedInspection = it.apply { it.uploadStatus.status = uploadStatus }
            info { "uploading status for inspection : ${it.id} with status ${it.uploadStatus.status}" }
            dao.insertAll(listOf(updatedInspection))
            updatedInspection
        }
    }

    private fun deleteSingle(it: Inspection, dao: RoomInspectionDao): Single<Inspection>? {
        return Single.fromCallable {
            info { "deleting inspection : ${it.id} after uploading" }
            dao.delete(it)
            it
        }
    }
}


