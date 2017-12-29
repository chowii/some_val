package com.sentia.android.base.androidbase

import android.os.Bundle
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.androidbase.api.RestAdapter
import com.sentia.android.base.androidbase.base.BaseActivity
import com.sentia.android.base.androidbase.feature1.Feature1Fragment
import android.arch.lifecycle.LifecycleOwner
import com.sentia.android.base.androidbase.util.RxBus

class MainActivity : BaseActivity(),LifecycleOwner {

    val rxBus by kodein.instance<RxBus>()
    val restAdapter by kodein.instance<RestAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(R.layout.activity_main)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, Feature1Fragment())
                .commit()

    }
}
