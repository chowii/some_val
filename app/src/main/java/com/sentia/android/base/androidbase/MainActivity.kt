package com.sentia.android.base.androidbase

import android.os.Bundle
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.androidbase.api.RestAdapter
import com.sentia.android.base.androidbase.api.RxBus

class MainActivity : BaseActivity() {

    val rxBus by kodein.instance<RxBus>()
    val restAdapter by kodein.instance<RestAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rxBus.post("")
    }
}
