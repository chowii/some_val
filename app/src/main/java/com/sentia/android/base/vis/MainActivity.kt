package com.sentia.android.base.vis

import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import com.sentia.android.base.vis.base.BaseActivity
import com.sentia.android.base.vis.evaluation.inspection.inspection.InspectionFragment
import com.sentia.android.base.vis.util.tag

class MainActivity : BaseActivity(), LifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.fragment_container,
//                        supportFragmentManager
//                                .findFragmentByTag(SearchFragment.tag) ?: SearchFragment(), SearchFragment.tag)
//                .commit()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,
                        supportFragmentManager
                                .findFragmentByTag(InspectionFragment.tag)
                                ?: InspectionFragment.newInstance(123), InspectionFragment.tag)
                .commit()

    }

}

