package com.sentia.android.base.vis.evaluation.inspection

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.switchMap
import android.content.Context
import android.net.Uri
import android.util.Base64
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.base.BaseViewModel
import com.sentia.android.base.vis.data.InspectionRepository
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.sentialibrary.ImageCompressor
import com.sentia.android.base.vis.util.DateUtils
import com.sentia.android.base.vis.util.Resource
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by mariolopez on 9/1/18.
 */
class EvaluationViewModel : BaseViewModel() {

    override val repository by kodein.instance<InspectionRepository>()

    private val inspectionLiveData: MutableLiveData<Long> = MutableLiveData()
    private var mutableInspection: Inspection? = null //todo check .isInitialised bug updates?!
    private val mutationInspectionLiveData = MutableLiveData<Resource<Inspection>>()
    private val imageSubject: PublishSubject<Resource<String>> = PublishSubject.create()

    private val imageCompressor by kodein.instance<ImageCompressor>()
    val imageObservable: Observable<Resource<String>> = imageSubject.toFlowable(BackpressureStrategy.MISSING).toObservable()

    val currentInspection: LiveData<Resource<Inspection>> = switchMap(inspectionLiveData) {
        //this is lazy load so it caches the result on rotation
        if (mutableInspection == null) {
            repository.findInspection(it)
        } else {
            mutationInspectionLiveData
        }
    }

    fun findInspection(id: Long) {
        inspectionLiveData.value = id
    }

    fun saveTempChanges(inspectionFromDb: Inspection, mutation: (Inspection) -> Unit) {
        if (mutableInspection == null) {
            mutableInspection = inspectionFromDb
        }
        mutation(mutableInspection!!)
        mutationInspectionLiveData.value = Resource(Resource.Status.SUCCESS, mutableInspection!!)
    }

    fun persistChanges(): LiveData<Resource<Nothing>> = repository.addInspections(listOf(mutableInspection?.apply {
        inspectedDate = DateUtils.timeToDisplayableString(System.currentTimeMillis())
    }!!))

    fun compressAndDecodeImage(uri: Uri, context: Context): Observable<String> {
        imageSubject.onNext(Resource(Resource.Status.LOADING))
        return imageCompressor.compressAsByteArray(uri)
                .map { bytes -> Base64.encodeToString(bytes, Base64.DEFAULT) }
                .doOnNext {
                    imageSubject.onNext(Resource(Resource.Status.SUCCESS, it))
                    imageSubject.onComplete()
                }
                .doOnError { imageSubject.onNext(Resource(Resource.Status.ERROR)) }
    }
}