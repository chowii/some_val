package com.sentia.android.base.vis.data.remote

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.RestAdapter
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.util.Constants.Keys

/**
 * Created by mariolopez on 28/12/17.
 */
class RemoteDataSource {

    val kodein = LazyKodein { App.context!!.kodein }
    private val restAdapter by kodein.instance<RestAdapter>()

    fun getInspectionList() = restAdapter.projectApi.getInspections()

    fun login(email: String, password: String) = restAdapter.projectApi.loginUser(mapOf(
            Keys.User.EMAIL to email,
            Keys.User.PASSWORD to password))

    fun uploadInspection(inspection: Inspection) = restAdapter.projectApi.uploadInspection(inspection.id, mapOf("inspection" to inspection))

//    Initial values
    fun getLookUps() = restAdapter.projectApi.getLookUps()
    fun getDepots() = restAdapter.projectApi.getDepots()
}