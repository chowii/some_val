package com.sentia.android.base.vis.evaluation.photos

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseFragment
import com.sentia.android.base.vis.evaluation.inspection.EvaluationViewModel
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID

/**
 * Created by mariolopez on 10/1/18.
 */
class PhotosFragment : BaseFragment() {
    private var inspectionViewModel: EvaluationViewModel? = null
    private val vehicleId: Long by lazy { arguments?.getLong(KEY_INSPECTION_ID, 0) ?: 0 }

    override fun initViewModel() {
        inspectionViewModel = ViewModelProviders.of(activity).get(EvaluationViewModel::class.java)
        inspectionViewModel?.let { lifecycle.addObserver(it) }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_photos, container, false)
    }

    companion object {
        //        val tagFrag = this::class.java.simpleName
        fun newInstance(vehicleId: Long) = PhotosFragment().apply {
            arguments = Bundle().apply {

                putLong(KEY_INSPECTION_ID, vehicleId)
            }
        }
    }
}