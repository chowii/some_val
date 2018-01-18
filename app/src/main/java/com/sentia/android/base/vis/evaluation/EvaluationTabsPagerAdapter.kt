package com.sentia.android.base.vis.evaluation

/**
 * Created by mariolopez on 11/1/18.
 */

import android.content.res.Resources
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.evaluation.damages.DamagesFragment
import com.sentia.android.base.vis.evaluation.inspection.inspection.InspectionFragment
import com.sentia.android.base.vis.evaluation.photos.PhotosFragment

/**
 * Created by mariolopez on 8/1/18.
 */
class EvaluationTabsPagerAdapter(fm: FragmentManager, private val resources: Resources, private val inspectionId: Long) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = when (position) {

        TAB_INSPECTION -> InspectionFragment.newInstance(inspectionId)
        TAB_PHOTOS -> PhotosFragment.newInstance(inspectionId)
        TAB_DAMAGES -> DamagesFragment.newInstance(inspectionId)
        else -> Fragment()
    }

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        TAB_INSPECTION -> resources.getString(R.string.tab_evaluation_inspection)
        TAB_PHOTOS -> resources.getString(R.string.tab_evaluation_photos)
        TAB_DAMAGES -> resources.getString(R.string.tab_evaluation_damages)
        else -> ""
    }

    override fun getCount(): Int = TABS_EVALUATION

    companion object {
        private const val TABS_EVALUATION = 3
        private const val TAB_INSPECTION = 0
        private const val TAB_PHOTOS = 1
        private const val TAB_DAMAGES = 2
    }
}