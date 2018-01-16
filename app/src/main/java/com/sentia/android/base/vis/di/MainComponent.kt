package com.sentia.android.base.vis.di

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidModule
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.RestAdapter
import com.sentia.android.base.vis.api.auth.AuthManager
import com.sentia.android.base.vis.api.interceptor.AuthInterceptor
import com.sentia.android.base.vis.data.InspectionRepository
import com.sentia.android.base.vis.data.remote.RemoteDataSource
import com.sentia.android.base.vis.data.room.RoomInspectionDataSource
import com.sentia.android.base.vis.util.Constants
import com.sentia.android.base.vis.util.RxBus

/**
 * Created by mariolopez on 27/12/17.
 */

object MainComponent {

    fun mainComponent() = Kodein {
        import(androidModule)
        BaseAppComponent()
    }

    fun Kodein.Builder.BaseAppComponent() {

        bind<RestAdapter>() with provider { RestAdapter() }
        bind<RxBus>() with provider { RxBus.Create.instance }
        bind<RemoteDataSource>() with singleton { RemoteDataSource() }
        bind<RoomInspectionDataSource>() with singleton { RoomInspectionDataSource.buildPersistentVehicle(App.context!!) }    //this needs to be singleton bottom page https://developer.android.com/training/data-storage/room/index.html
        bind<InspectionRepository>() with singleton { InspectionRepository() }
        bind<Constants>() with singleton { Constants(App.context!!.resources) }

        //Rest Adapter
        bind<AuthManager>() with provider { AuthManager() }
        bind<AuthInterceptor>() with provider { AuthInterceptor(instance()) }
    }
}