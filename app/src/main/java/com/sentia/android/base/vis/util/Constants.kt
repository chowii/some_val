package com.sentia.android.base.vis.util

import android.content.res.Resources
import com.sentia.android.base.vis.R

/**
 * Created by mariolopez on 9/1/18.
 */
const val KEY_INSPECTION_ID = "Inspection_ID"
const val PASSWORD_MIN_LENGTH: Int = 6
const val SP_AUTH: String = "Auth shared preferences"

class Constants(val res: Resources) {
    companion object {
        lateinit var BASE_URL: String
    }

    fun init() {
        BASE_URL = res.getString(R.string.baseUrl)
    }
     interface Keys {
        interface User{
            companion object {
                val EMAIL = "email"
                val PASSWORD = "password"
            }
        }

    }
}