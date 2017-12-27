package com.sentia.android.base.androidbase.api

/**
 * Created by mariolopez on 27/12/17.
 */

import com.sentia.android.base.androidbase.api.model.SampleModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {
    @GET("searchservice.svc/mapsearch")
    fun getSample(@QueryMap options: Map<String, String>): Flowable<SampleModel>
}