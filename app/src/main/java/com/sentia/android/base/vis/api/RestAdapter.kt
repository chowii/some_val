package com.sentia.android.base.vis.api

/**
 * Created by mariolopez on 27/12/17.
 */

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.interceptor.AuthInterceptor
import com.sentia.android.base.vis.data.room.entity.Vehicle
import com.sentia.android.base.vis.util.Constants.Companion.BASE_URL
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class RestAdapter {
    val kodein: LazyKodein = LazyKodein { App.context!!.kodein }

    private val authInterceptor by kodein.instance<AuthInterceptor>()

    private val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(StatusCodeInterceptor()) todo-mario or whoever, refactor this if needed
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)

    private var projectApi: Api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(buildConverter())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
            .create(Api::class.java)


    private fun buildConverter(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().registerTypeAdapterFactory(DataTypeAdapterFactory()).create())
    }

    class DataTypeAdapterFactory : TypeAdapterFactory {

        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {

            val delegate = gson.getDelegateAdapter(this, type)
            val elementAdapter = gson.getAdapter(JsonElement::class.java)

            return object : TypeAdapter<T>() {

                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: T) {
                    delegate.write(out, value)
                }

                @Throws(IOException::class)
                override fun read(`in`: JsonReader): T {

                    var jsonElement = elementAdapter.read(`in`)
                    if (jsonElement.isJsonObject) {
                        val jsonObject = jsonElement.asJsonObject
                        if (jsonObject.has("data"))
                            if (jsonObject.get("data").isJsonObject) {
                                jsonElement = jsonObject.get("data")
                            } else if (jsonObject.get("data").isJsonArray && type.rawType.isAssignableFrom(List::class.java)) {
                                jsonElement = jsonObject.get("data")
                            }
                    }

                    return delegate.fromJsonTree(jsonElement)
                }
            }.nullSafe()
        }
    }

    fun getSampleList(): Observable<Vehicle> = projectApi.getSample(mapOf())
}