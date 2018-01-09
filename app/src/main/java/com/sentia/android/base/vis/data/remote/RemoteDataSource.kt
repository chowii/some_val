package com.sentia.android.base.vis.data.remote

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.RestAdapter
import com.sentia.android.base.vis.data.room.entity.Vehicle
import io.reactivex.Observable

/**
 * Created by mariolopez on 28/12/17.
 */
class RemoteDataSource {

    val kodein = LazyKodein { App.context!!.kodein }
    val restAdapter by kodein.instance<RestAdapter>()

    //    fun getVehicleList() = restAdapter.getVehicleList()
    fun getVehicleList() = Observable.just(emptyList<Vehicle>())!!
//    fun getVehicle(id:Long) = Observable.just(emptyList<Vehicle>())!!


}