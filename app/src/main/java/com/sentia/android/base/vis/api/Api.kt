package com.sentia.android.base.vis.api

/**
 * Created by mariolopez on 27/12/17.
 */

import com.sentia.android.base.vis.api.model.LoginResult
import com.sentia.android.base.vis.data.room.entity.Inspection
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @GET("inspections")
    fun getInspections(): Observable<List<Inspection>>

    @POST("sessions")
    fun loginUser(@Body map: Map<String, String>): Observable<LoginResult>

}