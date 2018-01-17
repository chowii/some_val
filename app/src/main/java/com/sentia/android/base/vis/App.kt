package com.sentia.android.base.vis

/**
 * Created by mariolopez on 27/12/17.
 */

import android.app.Application
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.androidModule
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.di.MainComponent.BaseAppComponent
import com.sentia.android.base.vis.util.Constants

open class App : Application(), KodeinAware {
    override val kodein = ConfigurableKodein()
    private val constants: Constants by lazy { kodein.instance<Constants>() }

    override fun onCreate() {
        super.onCreate()

        App.context = this
        kodein.addImport(androidModule)
        kodein.addConfig {
            BaseAppComponent()
        }

        constants.init()
    }

    companion object {
        @JvmField
        var context: App? = null
    }

}