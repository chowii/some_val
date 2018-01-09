package com.sentia.android.base.vis.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.RestAdapter
import com.sentia.android.base.vis.data.VehicleRepository
import com.sentia.android.base.vis.data.remote.RemoteDataSource
import com.sentia.android.base.vis.data.room.RoomVehicleDataSource
import com.sentia.android.base.vis.util.RxBus

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
        bind<RemoteDataSource>() with singleton { RemoteDataSource() }
        bind<RoomVehicleDataSource>() with singleton { RoomVehicleDataSource.buildPersistentVehicle(App.context!!) }    //this needs to be singleton bottom page https://developer.android.com/training/data-storage/room/index.html
        bind<VehicleRepository>() with singleton { VehicleRepository() }
    }
}