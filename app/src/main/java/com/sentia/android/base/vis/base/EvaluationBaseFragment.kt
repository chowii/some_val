package com.sentia.android.base.vis.base

import android.arch.lifecycle.ViewModelProviders
import com.sentia.android.base.vis.evaluation.inspection.EvaluationViewModel
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID

/**
 * Created by mariolopez on 12/1/18.
 */
abstract class EvaluationBaseFragment : BaseFragment() {

    protected var inspectionViewModel: EvaluationViewModel? = null
    protected val inspectionId: Long by lazy { arguments?.getLong(KEY_INSPECTION_ID, 0) ?: 0 }

    override fun initViewModel() {
        inspectionViewModel = ViewModelProviders.of(this.activity!!).get(EvaluationViewModel::class.java)
        inspectionViewModel?.let { lifecycle.addObserver(it) }
    }

}