package com.sentia.android.base.androidbase.util

import com.sentia.android.base.androidbase.util.Resource.Status.*
import com.sentia.android.base.androidbase.util.exception.AppException

/**
 * Created by mariolopez on 27/12/17.
 */
class Resource<T> private constructor(val status: Resource.Status, val data: T?, val exception: AppException?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(exception: AppException?): Resource<T> {
            return Resource(ERROR, null, exception)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}