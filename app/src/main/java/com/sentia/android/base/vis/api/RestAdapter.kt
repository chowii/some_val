package com.sentia.android.base.vis.api

/**
 * Created by mariolopez on 27/12/17.
 */

import com.google.gson.*
import com.sentia.android.base.vis.api.model.DataResult
import com.sentia.android.base.vis.data.room.entity.Vehicle
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


class RestAdapter {

    private var projectApi: Api = Retrofit.Builder()
            .baseUrl("https://some.domain.com.au")
            .addConverterFactory(getListingsGsonConverter())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(Api::class.java)


    //we parse just what we need
    inner class ListingTypeAdapter : JsonDeserializer<DataResult> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DataResult {

            val listingsResultObj = json.asJsonObject
                    .getAsJsonObject("data")

            return Gson().fromJson<DataResult>(listingsResultObj, DataResult::class.java)
        }

    }

    private fun getListingsGsonConverter(): GsonConverterFactory {
        val gson = GsonBuilder()
                .registerTypeAdapter(DataResult::class.java, ListingTypeAdapter())
                .create()
        return GsonConverterFactory.create(gson)
    }

    fun getSampleList(): Observable<Vehicle> = projectApi.getSample(mapOf())
}