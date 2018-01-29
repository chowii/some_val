package com.sentia.android.base.vis.api

/**
 * Created by mariolopez on 27/12/17.
 */

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.google.gson.*
import com.google.gson.annotations.Expose
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.interceptor.AuthInterceptor
import com.sentia.android.base.vis.api.interceptor.StatusCodeInterceptor
import com.sentia.android.base.vis.data.room.entity.UploadStatus
import com.sentia.android.base.vis.util.Constants.Companion.BASE_URL
import com.sentia.android.base.vis.util.orFalse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class RestAdapter {
    private val kodein: LazyKodein = LazyKodein { App.context!!.kodein }

    private val authInterceptor by kodein.instance<AuthInterceptor>()

    private val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(StatusCodeInterceptor())
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)

    var projectApi: Api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(buildConverter())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
            .create(Api::class.java)

    private fun buildConverter(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder()
                .addSerializationExclusionStrategy(SerializeExclStrategy())
                .addDeserializationExclusionStrategy(DeserializeExclStrategy())
                .excludeFieldsWithModifiers()
                .registerTypeAdapterFactory(DataTypeAdapterFactory()).create())
    }

    inner class SerializeExclStrategy : ExclusionStrategy {
        override fun shouldSkipField(f: FieldAttributes?): Boolean {
            val expose = f?.getAnnotation(Expose::class.java)
            val excludeDeserialize = expose?.toString()?.contains("serialize=false").orFalse()
            val excludeSerializable = f?.name?.contains("serialVersionUID").orFalse()
            return excludeDeserialize || excludeSerializable
        }

        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
            val isUploadState = clazz?.simpleName.equals(UploadStatus::class.java.simpleName).orFalse()
            if (isUploadState) {
                return true
            }
            return false
        }
    }
    inner class DeserializeExclStrategy : ExclusionStrategy {
        override fun shouldSkipField(f: FieldAttributes?): Boolean {
            val expose = f?.getAnnotation(Expose::class.java)
            val excludeDeserialize = expose?.toString()?.contains("deserialize=false").orFalse()
            val excludeSerializable = f?.name?.contains("serialVersionUID").orFalse()
            return excludeDeserialize || excludeSerializable
        }

        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
            val isUploadState = clazz?.simpleName.equals(UploadStatus::class.java.simpleName).orFalse()
            if (isUploadState) {
                return true
            }
            return false
        }
    }

    inner class DataTypeAdapterFactory : TypeAdapterFactory {

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

}