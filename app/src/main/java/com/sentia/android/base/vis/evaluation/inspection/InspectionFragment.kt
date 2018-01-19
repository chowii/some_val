package com.sentia.android.base.vis.evaluation.inspection.inspection

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.EvaluationBaseFragment
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.databinding.FragmentInspectionBinding
import com.sentia.android.base.vis.evaluation.inspection.InspectionTabsPagerAdapter
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID
import com.sentia.android.base.vis.util.Resource
import kotlinx.android.synthetic.main.fragment_inspection.*
import org.jetbrains.anko.info

/**
 * Created by mariolopez on 3/1/18.
 */
class InspectionFragment : EvaluationBaseFragment() {

    private lateinit var binding: FragmentInspectionBinding

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inspection, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(savedInstanceState)
    }

    private fun initUi(savedInstanceState: Bundle?) {
        tl_inspection.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }
        })
        tl_inspection.setupWithViewPager(vp_tabs_inspection)
        vp_tabs_inspection.offscreenPageLimit = InspectionTabsPagerAdapter.TABS_INSPECTION_COUNT
        vp_tabs_inspection.adapter = InspectionTabsPagerAdapter(childFragmentManager, resources, inspectionId)
        vp_tabs_inspection.currentItem = InspectionTabsPagerAdapter.TAB_VEHICLE

        inspectionViewModel?.findInspection(inspectionId)
        inspectionViewModel?.currentInspection
                ?.observe(this, Observer<Resource<Inspection>?> {
                    info { it?.data?.id }
                })
    }

    companion object {
        fun newInstance(inspectionId: Long) = InspectionFragment().apply {
            arguments = Bundle().apply {
                putLong(KEY_INSPECTION_ID, inspectionId)
            }
        }
    }
}