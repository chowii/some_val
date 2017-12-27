package com.sentia.android.base.androidbase

/**
 * Created by mariolopez on 27/12/17.
 */

import android.app.Application
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.sentia.android.base.androidbase.di.MainComponent.BaseAppComponent

open class App : Application(), KodeinAware {
    override val kodein = ConfigurableKodein()

    override fun onCreate() {
        super.onCreate()

        App.context = this
        kodein.addConfig {
            BaseAppComponent()
        }
    }

    companion object {
        @JvmField
        var context: App? = null
    }

}