package com.sentia.android.base.vis.data.remote

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.RestAdapter
import com.sentia.android.base.vis.data.room.entity.Inspection
import io.reactivex.Observable

/**
 * Created by mariolopez on 28/12/17.
 */
class RemoteDataSource {

    val kodein = LazyKodein { App.context!!.kodein }
    val restAdapter by kodein.instance<RestAdapter>()

    //    fun getInspectionList() = restAdapter.getInspectionList()
    fun getInspectionList() = Observable.just(emptyList<Inspection>())!!
//    fun getInspection(id:Long) = Observable.just(emptyList<Vehicle>())!!


}