package com.sentia.android.base.androidbase.feature1

import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.androidbase.BaseFragment
import com.sentia.android.base.androidbase.api.RestAdapter
import com.sentia.android.base.androidbase.api.RxBus

/**
 * Created by mariolopez on 27/12/17.
 */
class Feature1Fragment : BaseFragment() {
    val rxBus by kodein.instance<RxBus>()
    val RestAdapter by kodein.instance<RestAdapter>()
}