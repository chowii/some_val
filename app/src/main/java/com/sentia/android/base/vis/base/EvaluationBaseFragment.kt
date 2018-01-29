package com.sentia.android.base.vis.base

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModelProviders
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.evaluation.inspection.EvaluationViewModel
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID
import com.sentia.android.base.vis.util.Resource
import io.reactivex.Observable
import org.reactivestreams.Publisher

/**
 * Created by mariolopez on 12/1/18.
 */
abstract class EvaluationBaseFragment : BaseFragment() {

    protected lateinit var inspectionViewModel: EvaluationViewModel
    protected val inspectionId: Long by lazy { arguments?.getLong(KEY_INSPECTION_ID, 0) ?: 0 }

    private val inspectionPublisher: Publisher<Resource<Inspection>>? by lazy {
        LiveDataReactiveStreams
                .toPublisher(this, inspectionViewModel.currentInspection)
    }
    protected val inspectionDbObs: Observable<Inspection> by lazy {
        Observable.fromPublisher(inspectionPublisher)
                .map { it.data!! }
                .firstOrError()
                .toObservable()
                .share()
    }

    override fun initViewModel() {
        inspectionViewModel = ViewModelProviders.of(this.activity!!).get(EvaluationViewModel::class.java)
        inspectionViewModel.let { lifecycle.addObserver(it) }
    }

}