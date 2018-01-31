package com.sentia.android.base.vis.api

/**
 * Created by mariolopez on 27/12/17.
 */

import com.sentia.android.base.vis.api.model.LoginResult
import com.sentia.android.base.vis.data.room.entity.Inspection
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface Api {


    @GET(PATH_INSPECTIONS)
    fun getInspections(): Single<List<Inspection>>

    @POST("sessions")
    fun loginUser(@Body map: Map<String, String>): Single<LoginResult>

    @PUT(PATH_INSPECTIONS + "/{id}")
    fun uploadInspection(@Path("id") id: Long, @Body map: Map<String,Inspection>): Observable<Inspection>

    companion object {
        private const val PATH_INSPECTIONS = "inspections"
    }
}