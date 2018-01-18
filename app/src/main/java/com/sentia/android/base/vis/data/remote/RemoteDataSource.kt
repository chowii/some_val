package com.sentia.android.base.vis.data.remote

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.RestAdapter
import com.sentia.android.base.vis.util.Constants.Keys

/**
 * Created by mariolopez on 28/12/17.
 */
class RemoteDataSource {

    val kodein = LazyKodein { App.context!!.kodein }
    private val restAdapter by kodein.instance<RestAdapter>()

    //    fun getInspectionList() = restAdapter.getInspectionList()
    fun getInspectionList() = restAdapter.projectApi.getInspections()

    //    fun getInspection(id:Long) = Observable.just(emptyList<Vehicle>())!!

    fun login(email: String, password: String) = restAdapter.projectApi.loginUser(mapOf(
            Keys.User.EMAIL to email,
            Keys.User.PASSWORD to password))

}