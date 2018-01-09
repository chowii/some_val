package com.sentia.android.base.vis.api

/**
 * Created by mariolopez on 27/12/17.
 */

import com.sentia.android.base.vis.data.room.entity.Vehicle
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {
    @GET("searchservice.svc/mapsearch")
    fun getSample(@QueryMap options: Map<String, String>): Observable<Vehicle>
}