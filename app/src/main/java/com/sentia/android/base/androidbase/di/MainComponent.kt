package com.sentia.android.base.androidbase.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import com.sentia.android.base.androidbase.api.RestAdapter
import com.sentia.android.base.androidbase.util.RxBus

/**
 * Created by mariolopez on 27/12/17.
 */

object MainComponent {

    fun mainComponent() = Kodein {
        BaseAppComponent()
    }

    fun Kodein.Builder.BaseAppComponent() {

        bind<RestAdapter>() with provider { RestAdapter() }
        bind<RxBus>() with provider { RxBus.Create.instance }

    }
}