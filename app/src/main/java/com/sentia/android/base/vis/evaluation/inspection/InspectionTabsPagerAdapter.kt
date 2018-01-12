package com.sentia.android.base.vis.evaluation.inspection

import android.content.res.Resources
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.evaluation.inspection.accessories.AccessoriesInspectFragment
import com.sentia.android.base.vis.evaluation.inspection.others.OthersInspectFragment
import com.sentia.android.base.vis.evaluation.inspection.servicing.ServicingInspectFragment
import com.sentia.android.base.vis.evaluation.inspection.tyres.TyresInspectFragment
import com.sentia.android.base.vis.evaluation.inspection.vehicle.VehicleInspectFragment

/**
 * Created by mariolopez on 8/1/18.
 */
class InspectionTabsPagerAdapter(fm: FragmentManager, private val resources: Resources,
                                 private val vehicleId: Long) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = when (position) {
        TAB_VEHICLE -> VehicleInspectFragment.newInstance(vehicleId)
        TAB_TYRES -> TyresInspectFragment.newInstance(vehicleId)
        TAB_SERVICING -> ServicingInspectFragment.newInstance(vehicleId)
        TAB_ACCESSORIES -> AccessoriesInspectFragment.newInstance(vehicleId)
        TAB_OTHERS -> OthersInspectFragment.newInstance(vehicleId)
        else -> Fragment()
    }

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        TAB_VEHICLE -> resources.getString(R.string.tab_vehicle)
        TAB_TYRES -> resources.getString(R.string.tab_tyres)
        TAB_SERVICING -> resources.getString(R.string.tab_servicing)
        TAB_ACCESSORIES -> resources.getString(R.string.tab_accessories)
        TAB_OTHERS -> resources.getString(R.string.tab_others)
        else -> ""
    }

    override fun getCount(): Int = TABS_INSPECTION_COUNT

    companion object {
        internal val TABS_INSPECTION_COUNT = 5
        internal const val TAB_VEHICLE = 0
        private const val TAB_TYRES = 1
        private const val TAB_SERVICING = 2
        private const val TAB_ACCESSORIES = 3
        private const val TAB_OTHERS = 4
    }
}