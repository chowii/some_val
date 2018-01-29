package com.sentia.android.base.vis.di

import android.content.Context
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.KodeinSharedPreferencesInfo
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.RestAdapter
import com.sentia.android.base.vis.api.auth.AuthManager
import com.sentia.android.base.vis.api.interceptor.AuthInterceptor
import com.sentia.android.base.vis.data.InspectionRepository
import com.sentia.android.base.vis.data.remote.RemoteDataSource
import com.sentia.android.base.vis.data.room.RoomInspectionDataSource
import com.sentia.android.base.vis.sentialibrary.ImageCompressor
import com.sentia.android.base.vis.upload.Uploader
import com.sentia.android.base.vis.util.Constants
import com.sentia.android.base.vis.util.RxBus
import com.sentia.android.base.vis.util.SP_AUTH
import com.sentia.android.base.vis.util.connectivity.NetworkStatusManager

/**
 * Created by mariolopez on 27/12/17.
 */

object MainComponent {

    fun mainComponent() = Kodein {
        BaseAppComponent()
    }

    fun Kodein.Builder.BaseAppComponent() {

        bind<RestAdapter>() with singleton { RestAdapter() }
        bind<Context>() with provider { App.context!! }

        bind<RxBus>() with provider { RxBus.Create.instance }
        bind<RemoteDataSource>() with singleton { RemoteDataSource() }
        bind<RoomInspectionDataSource>() with singleton {
            RoomInspectionDataSource.buildPersistentVehicle(instance())
        }    //this needs to be singleton look at the bottom of the page https://developer.android.com/training/data-storage/room/index.html
        bind<InspectionRepository>() with provider { InspectionRepository() }

        bind<Constants>() with singleton { Constants(instance()) }
        bind<AuthManager>() with provider {
            AuthManager(KodeinSharedPreferencesInfo(instance(), SP_AUTH).getSharedPreferences())
        }

        bind<AuthInterceptor>() with provider { AuthInterceptor(instance()) }

        bind<ImageCompressor>() with singleton { ImageCompressor(instance()) }

        bind<NetworkStatusManager>() with provider { NetworkStatusManager(instance()) }
        bind<Uploader>() with provider { Uploader(instance(), instance(), instance()) }
    }
}