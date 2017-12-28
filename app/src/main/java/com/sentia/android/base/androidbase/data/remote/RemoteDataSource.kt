package com.sentia.android.base.androidbase.data.remote

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.androidbase.App
import com.sentia.android.base.androidbase.api.RestAdapter
import com.sentia.android.base.androidbase.data.room.entity.SampleModel
import io.reactivex.Observable

/**
 * Created by mariolopez on 28/12/17.
 */
class RemoteDataSource {

    val kodein = LazyKodein { App.context!!.kodein }
    val restAdapter by kodein.instance<RestAdapter>()

    //    fun getSampleList() = restAdapter.getSampleList()
    fun getSampleList() = Observable.just(listOf(
            SampleModel(32, 1263, "mario"),
            SampleModel(12, 15623, "jovan"),
            SampleModel(33, 12353, "julia"),
            SampleModel(29, 12333, "sabibb"),
            SampleModel(11, 1223, "sam"),
            SampleModel(10, 12213, "zhang"),
            SampleModel(44, 1213, "tom"),
            SampleModel(55, 17213, "micheal")))!!


}