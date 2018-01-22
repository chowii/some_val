package com.sentia.android.base.vis.di

import android.content.Context
import com.github.salomonbrys.kodein.*
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.RestAdapter
import com.sentia.android.base.vis.api.auth.AuthManager
import com.sentia.android.base.vis.api.interceptor.AuthInterceptor
import com.sentia.android.base.vis.data.InspectionRepository
import com.sentia.android.base.vis.data.remote.RemoteDataSource
import com.sentia.android.base.vis.data.room.RoomInspectionDataSource
import com.sentia.android.base.vis.util.Constants
import com.sentia.android.base.vis.util.RxBus
import com.sentia.android.base.vis.util.SP_AUTH

/**
 * Created by mariolopez on 27/12/17.
 */

object MainComponent {

    fun mainComponent() = Kodein {
        BaseAppComponent()
    }

    fun Kodein.Builder.BaseAppComponent() {

        bind<RestAdapter>() with singleton { RestAdapter() }

        bind<RxBus>() with provider { RxBus.Create.instance }
        bind<RemoteDataSource>() with singleton { RemoteDataSource() }
        bind<RoomInspectionDataSource>() with singleton { RoomInspectionDataSource.buildPersistentVehicle(App.context!!) }    //this needs to be singleton bottom page https://developer.android.com/training/data-storage/room/index.html
        bind<InspectionRepository>() with provider { InspectionRepository() }


        //todo-tem change this after kode in issue for import module is fixed
        bind<Constants>() with singleton { Constants(App.context!!.resources) }
//        bind<Constants>() with singleton { Constants(instance()) }
        bind<AuthManager>() with provider { AuthManager(App.context!!.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)) }
//        bind<AuthManager>() with provider { AuthManager(instance()) }
        //**********************/


        bind<AuthInterceptor>() with provider { AuthInterceptor(instance()) }
    }
}