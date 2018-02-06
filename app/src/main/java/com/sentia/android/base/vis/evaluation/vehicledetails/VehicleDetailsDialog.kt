package com.sentia.android.base.vis.evaluation.vehicledetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.databinding.DialogVehicleDetailsBinding
import com.sentia.android.base.vis.evaluation.inspection.EvaluationViewModel
import com.sentia.android.base.vis.util.Resource
import com.sentia.android.base.vis.util.toDisplayableString
import kotlinx.android.synthetic.main.dialog_vehicle_details.*


class VehicleDetailsDialog : DialogFragment() {

    private lateinit var inspectionViewModel: EvaluationViewModel
    private lateinit var binding: DialogVehicleDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_vehicle_details, container, true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_close.setOnClickListener { dismiss() }
        inspectionViewModel.currentInspection
                .observe(this, Observer<Resource<Inspection>?> {
                    it?.data?.let {
                        binding.vehicle = it.vehicle
                        binding.accessories = it.accessories.toDisplayableString()
                    }
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        // dialog width has to be set programmatically here because WRAP_CONTENT for ConstraintLayout doesn't work
        val width = (resources.displayMetrics.widthPixels * 0.8).toInt()
        dialog.window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun initViewModel() {
        inspectionViewModel = ViewModelProviders.of(this.activity!!).get(EvaluationViewModel::class.java)
        inspectionViewModel.let { lifecycle.addObserver(it) }
    }

    companion object {
        const val TAG = "VehicleDetailsDialog"
    }
}