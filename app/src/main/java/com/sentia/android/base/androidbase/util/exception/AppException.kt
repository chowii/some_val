package com.sentia.android.base.androidbase.util.exception

import java.lang.Exception

/**
 * Created by mariolopez on 27/12/17.
 */
data class AppException(val exceptIn: Throwable?): Exception()