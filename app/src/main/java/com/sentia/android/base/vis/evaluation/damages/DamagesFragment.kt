package com.sentia.android.base.vis.evaluation.damages

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
class DamagesFragment : BaseFragment() {
    private var inspectionViewModel: EvaluationViewModel? = null

    override fun initViewModel() {
        inspectionViewModel =  ViewModelProviders.of(activity!!).get(EvaluationViewModel::class.java)
        inspectionViewModel?.let { lifecycle.addObserver(it) }
    }

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_damages, container, false)
    }

    companion object {
        //        val tagFrag = this::class.java.simpleName
        fun newInstance(vehicleId: Long) = DamagesFragment().apply {
            arguments = Bundle().apply {
                putLong(KEY_INSPECTION_ID, vehicleId)
            }
        }
    }
}