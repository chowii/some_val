package com.sentia.android.base.vis.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.api.model.LoginResult
import com.sentia.android.base.vis.data.remote.RemoteDataSource
import com.sentia.android.base.vis.data.repository.BaseRepository
import com.sentia.android.base.vis.data.room.RoomInspectionDataSource
import com.sentia.android.base.vis.data.room.entity.Image
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.data.room.entity.InspectionImage
import com.sentia.android.base.vis.data.room.entity.Vehicle
import com.sentia.android.base.vis.util.Resource
import com.sentia.android.base.vis.util.Resource.Status.*
import com.sentia.android.base.vis.util.exception.AppException
import com.sentia.android.base.vis.util.forUi
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.error


/**
 * Created by mariolopez on 27/12/17.
 */
class InspectionRepository : BaseRepository() {

    private val remoteDataSource by kodein.instance<RemoteDataSource>()
    private val roomVehicleDataSource by kodein.instance<RoomInspectionDataSource>()

    override fun addMockedInspections() {
        val inspectionDao = roomVehicleDataSource.inspectionDao()

        val inspections = RoomInspectionDataSource.getAllInspections()
        inspectionDao.insertAll(inspections)

        val images = inspections.flatMap { inspection -> inspection.images }
        inspectionDao.insertAllImages(images)

        val inspectionImages = inspections.flatMap { inspection ->
            inspection.images.map { image ->
                InspectionImage(inspection.id, image.id)
            }
        }
        inspectionDao.insertAllInspectionImages(inspectionImages)
    }

    private fun addInspection(inspections: List<Inspection>) {
        roomVehicleDataSource.inspectionDao().insertAll(inspections)

    }

    override fun findInspection(inspectionId: Long): LiveData<Resource<Inspection>> {
        val dao = roomVehicleDataSource.inspectionDao()
        val inspectionFlowable = Observable.combineLatest(
                dao.getInspectionF(inspectionId).toObservable(),
                dao.getInspectionPhotos(inspectionId).toObservable(),

                BiFunction { inspection: Inspection, photos: List<Image> ->
                    inspection.images.addAll(photos)
                    inspection
                })

                .map { Resource(SUCCESS, it) }
                .toFlowable(BackpressureStrategy.ERROR)

        return LiveDataReactiveStreams.fromPublisher<Resource<Inspection>>(inspectionFlowable)

    }


    override fun getInspections(): LiveData<Resource<List<Inspection>>> {
        val result = MediatorLiveData<Inspection>()
        val liveResult = MutableLiveData<Resource<List<Inspection>>>()
        val resultLiveAndRoom = MutableLiveData<Resource<List<Inspection>>>()

        liveResult.value = Resource(LOADING)

        //todo this logic needs to be refactored once we know how the api will work
        //todo at the current moment we just add both sources so the loading feeding won't be effective 100%

        result.addSource(roomVehicleDataSource.inspectionDao().getAllInspections(),
                { inspection: List<Inspection>? ->
                    resultLiveAndRoom.value = Resource(SUCCESS, inspection)
                })
        result.addSource(liveResult,
                { inspectionResult: Resource<List<Inspection>>? -> resultLiveAndRoom.value = inspectionResult })

        compositeDisposable += remoteDataSource.getInspectionList()
                .forUi()
                .subscribe(
                        {
                            addInspection(it)
                            liveResult.value = Resource(SUCCESS, it)
                        },
                        {
                            error { it }
                            liveResult.value = Resource(ERROR, null, AppException(it))
                        })
        return resultLiveAndRoom
    }

    override fun getTotalInspections() = roomVehicleDataSource.inspectionDao().getTotalInspections()

    fun doSearch(search: String?): LiveData<Resource<List<Vehicle>>> {
        // todo have logic here to get data from db or/and online then filter the list
        return MutableLiveData()
    }

    fun login(email: String, password: String): LiveData<Resource<LoginResult>> {
        val liveData = MutableLiveData<Resource<LoginResult>>()
        liveData.value = Resource(LOADING)

        compositeDisposable += remoteDataSource.login(email, password)
                .forUi()
                .subscribeBy(
                        onNext = { liveData.value = Resource(SUCCESS, it) },
                        onError = { liveData.value = Resource(ERROR, null, AppException(it)) })

        return liveData

    }
}
