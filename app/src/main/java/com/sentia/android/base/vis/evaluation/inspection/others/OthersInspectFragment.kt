package com.sentia.android.base.vis.evaluation.inspection.others

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseFragment
import com.sentia.android.base.vis.databinding.FragmentInspectionsOthersBinding
import com.sentia.android.base.vis.search.SearchViewModel
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID

/**
 * Created by mariolopez on 9/1/18.
 */
class OthersInspectFragment : BaseFragment() {
    private var othersViewModel: SearchViewModel? = null

    override fun initViewModel() {
        othersViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        othersViewModel?.let { lifecycle.addObserver(it) }
    }

    private lateinit var binding: FragmentInspectionsOthersBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inspections_others, container, false)
//        initUi(savedInstanceState)

        return binding.root
    }

    companion object {
        fun newInstance(vehicleId: Long) = OthersInspectFragment().apply {
            arguments = Bundle().apply {

                putLong(KEY_INSPECTION_ID, vehicleId)
            }
        }
    }
}