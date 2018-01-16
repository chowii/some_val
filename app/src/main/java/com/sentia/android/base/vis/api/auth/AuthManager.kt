package com.sentia.android.base.vis.api.auth

import android.content.SharedPreferences
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.App

/**
 * Created by mariolopez on 16/1/18.
 */
class AuthManager {
    val kodein: LazyKodein = LazyKodein { App.context!!.kodein }
    private val sharedPreferences: SharedPreferences by kodein.instance()

    fun getAuthToken(): String {
        return  sharedPreferences.getString("Auth token","")
    }

    companion object {
        private val SP_AUTH_TOKEN_KEY : String = "Auth token"
    }
}