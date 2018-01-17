package com.sentia.android.base.vis.api.interceptor

import com.sentia.android.base.vis.api.auth.AuthManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by mariolopez on 16/1/18.
 */
class AuthInterceptor(private val authManager: AuthManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val authToken = authManager.getAuthToken()
        val requestBuilder = request.newBuilder()

        if (authToken.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", authToken)
        }
        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }

}