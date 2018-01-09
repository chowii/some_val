package com.sentia.android.base.vis.evaluation.inspection.inspection

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseFragment
import com.sentia.android.base.vis.data.room.entity.Vehicle
import com.sentia.android.base.vis.databinding.FragmentInspectionBinding
import com.sentia.android.base.vis.evaluation.inspection.InspectionTabsPagerAdapter
import com.sentia.android.base.vis.evaluation.inspection.InspectionViewModel
import com.sentia.android.base.vis.util.BUNDLE_VEHICLE_ID
import com.sentia.android.base.vis.util.Resource
import kotlinx.android.synthetic.main.fragment_inspection.*
import org.jetbrains.anko.info

/**
 * Created by mariolopez on 3/1/18.
 */
class InspectionFragment : BaseFragment() {

    private var inspectionViewModel: InspectionViewModel? = null
    private val vehicleId: Long by lazy { arguments?.getLong(BUNDLE_VEHICLE_ID, 0) ?: 0 }

    override fun initViewModel() {
        inspectionViewModel = ViewModelProviders.of(activity).get(InspectionViewModel::class.java)
        inspectionViewModel?.let { lifecycle.addObserver(it) }
        inspectionViewModel?.initLocalVehicles()
    }

    private lateinit var binding: FragmentInspectionBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inspection, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
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
        vp_tabs_inspection.adapter = InspectionTabsPagerAdapter(activity.supportFragmentManager, resources)

        inspectionViewModel?.findVehicle(vehicleId)
        inspectionViewModel?.vehicleResult
                ?.observe(this, Observer<Resource<Vehicle>?> {
                    info { it?.data?.id }
                })
        //todo init recycler view
//        searchViewModel?.loadVehicles()?.observe(this, Observer {
//            val size = it?.data?.size
//            when (it?.status) {
//                Resource.Status.SUCCESS -> TODO()
//                Resource.Status.ERROR -> TODO()
//                Resource.Status.LOADING -> TODO()
//            }
//            todo-init recycler view
//        })
//        searchViewModel?.vehicleResult?.observe(this, Observer {
//            todo
//        })
//        searchViewModel?.search()
//        restore simple Ui
//        val keywords = savedInstanceState?.getString(SearchFragment.KEY_SEARCH_KEY_WORD).orEmpty()
//        binding.searchKeyWord = keywords
    }

    companion object {
        //        val tagFrag = this::class.java.simpleName
        fun newInstance(vehicleId: Long) = InspectionFragment().apply {
            arguments = Bundle().apply {

                putLong(BUNDLE_VEHICLE_ID, vehicleId)
            }
        }
    }
}