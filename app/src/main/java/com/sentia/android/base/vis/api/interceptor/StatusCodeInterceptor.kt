//package com.sentia.android.base.vis.api.interceptor
//
//import okhttp3.Interceptor
//import okhttp3.Response
//import java.io.IOException
//
///**
// * Created by mariolopez on 16/1/18.
// */
//class StatusCodeInterceptor : Interceptor {
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//
//        val response = chain.proceed(request)
//
//        val statusCode = response.code()
//        if (statusCode < 200 || statusCode > 299 || response.body() == null) {
//            DEPENDENCIES.logger.d("InterceptStatusCode: $statusCode")
//            handleErrorResponse(response)
//        }
//
//        return response
//    }
//
//    @Throws(HttpStatusException::class)
//    private fun handleErrorResponse(response: Response?) {
//        val statusCode = response!!.code()
//        when {
//
//            statusCode == 401 -> throw HttpStatusException("Your session has expired, please relogin."
//                    , HttpErrorType.ReloginRequired)
//
//            statusCode == 419 -> throw HttpStatusException("Pin re-entry required to continue."
//                    , HttpErrorType.PasswordReEnterRequired)
//
//            statusCode == 480 -> throw HttpStatusException("Pin has expired, please request a new one."
//                    , HttpErrorType.PasswordResetRequired)
//
//            false -> throw HttpStatusException(SohoError().error, HttpErrorType.General)
//
//            else -> {
//                val error = SohoError.fromResponse(response)
//                throw HttpStatusException(error.message, HttpErrorType.General)
//            }
//        }
//    }
//}