package com.sentia.android.base.vis.evaluation.inspection.vehicle

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseFragment
import com.sentia.android.base.vis.databinding.FragmentInspectionVehicleBinding
import com.sentia.android.base.vis.search.SearchViewModel

/**
 * Created by mariolopez on 9/1/18.
 */
class VehicleInspFragment : BaseFragment(){
    private var inspectionViewModel: SearchViewModel? = null

    override fun initViewModel() {
        inspectionViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        inspectionViewModel?.let { lifecycle.addObserver(it) }
        inspectionViewModel?.initLocalVehicles()
    }

    private lateinit var binding: FragmentInspectionVehicleBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inspection_vehicle, container, false)
//        initUi(savedInstanceState)

        return binding.root
    }
}