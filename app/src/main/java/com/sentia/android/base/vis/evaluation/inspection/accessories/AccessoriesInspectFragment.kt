package com.sentia.android.base.vis.evaluation.inspection.accessories

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseFragment
import com.sentia.android.base.vis.databinding.FragmentInspectionAccessoriesBinding
import com.sentia.android.base.vis.search.SearchViewModel
import com.sentia.android.base.vis.util.KEY_VEHICLE_ID

/**
 * Created by mariolopez on 9/1/18.
 */
class AccessoriesInspectFragment : BaseFragment() {
    private var accessoriesViewModel: SearchViewModel? = null

    override fun initViewModel() {
        accessoriesViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        accessoriesViewModel?.let { lifecycle.addObserver(it) }
    }

    private lateinit var binding: FragmentInspectionAccessoriesBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inspection_accessories, container, false)
//        initUi(savedInstanceState)

        return binding.root
    }

    companion object {
        fun newInstance(vehicleId: Long) = AccessoriesInspectFragment().apply {
            arguments = Bundle().apply {
                putLong(KEY_VEHICLE_ID, vehicleId)
            }
        }
    }
}