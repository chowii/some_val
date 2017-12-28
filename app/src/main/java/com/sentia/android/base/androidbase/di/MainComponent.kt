package com.sentia.android.base.androidbase.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import com.sentia.android.base.androidbase.App
import com.sentia.android.base.androidbase.api.RestAdapter
import com.sentia.android.base.androidbase.data.SampleRepository
import com.sentia.android.base.androidbase.data.remote.RemoteDataSource
import com.sentia.android.base.androidbase.data.room.RoomSampleDataSource
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
        bind<RemoteDataSource>() with singleton { RemoteDataSource() }
        bind<RoomSampleDataSource>() with singleton { RoomSampleDataSource.buildPersistentSamples(App.context!!) }
        bind<SampleRepository>() with singleton { SampleRepository() }
    }
}