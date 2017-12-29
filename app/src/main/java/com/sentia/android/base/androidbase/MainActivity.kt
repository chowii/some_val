package com.sentia.android.base.androidbase

import android.arch.lifecycle.LifecycleOwner
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.androidbase.api.RestAdapter
import com.sentia.android.base.androidbase.api.model.SampleBindingModel
import com.sentia.android.base.androidbase.base.BaseActivity
import com.sentia.android.base.androidbase.databinding.ActivityMainBinding
import com.sentia.android.base.androidbase.util.RxBus

class MainActivity : BaseActivity(),LifecycleOwner {

    val rxBus by kodein.instance<RxBus>()
    val restAdapter by kodein.instance<RestAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //example for Di we retrieve the instance just the first time we use it
        rxBus.post("")

        val sample = SampleBindingModel("Mario", 31)
        binding.setVariable(BR.sampleModel, sample)
    }
}
