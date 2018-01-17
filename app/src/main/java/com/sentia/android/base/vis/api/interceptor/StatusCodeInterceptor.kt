package com.sentia.android.base.vis.api.interceptor

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.auth.AuthManager
import okhttp3.Interceptor
import okhttp3.Response
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import java.io.IOException
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

/**
 * Created by mariolopez on 16/1/18.
 */
class StatusCodeInterceptor : Interceptor, AnkoLogger {
    val kodein: LazyKodein = LazyKodein { App.context!!.kodein }
     private val authManager: AuthManager by kodein.instance()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

        val statusCode = response.code()
        if (statusCode < HTTP_OK || statusCode > 299 || response.body() == null) {
            debug { "Intercepted Status Code: $statusCode" }
            handleErrorResponse(response)
        }

        return response
    }

    private fun handleErrorResponse(response: Response?) {
        val statusCode =
                when (response?.code()) {
                    HTTP_UNAUTHORIZED -> authManager.logout()

//            statusCode == 419 -> throw HttpStatusException("Pin re-entry required to continue."
//                    , HttpErrorType.PasswordReEnterRequired)
//
//            statusCode == 480 -> throw HttpStatusException("Pin has expired, please request a new one."
//                    , HttpErrorType.PasswordResetRequired)
//
//            false -> throw HttpStatusException(SohoError().error, HttpErrorType.General)
//
                    else -> {
                        info { "status code not handled" }
                    }
                }
    }
}