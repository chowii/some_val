package com.sentia.android.base.vis.api.auth

import android.content.SharedPreferences
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.model.LoginResult
import com.sentia.android.base.vis.util.RxBus

/**
 * Created by mariolopez on 16/1/18.
 */
class AuthManager(private val sp: SharedPreferences) {
    val kodein: LazyKodein = LazyKodein { App.context!!.kodein }
    private val rxBus: RxBus by kodein.instance()

    fun getAuthToken(): String {
        return sp.getString(SP_KEY_AUTH_TOKEN, "")
    }

    companion object {
        const val SP_KEY_AUTH_TOKEN = "Auth token"
    }

    fun isUserLogged(): Boolean = getAuthToken().isNotEmpty()

    fun logout() {
        sp.edit().clear().apply()
        rxBus.post(NotAuthorised())
    }

    fun login(loginResult: LoginResult?) {
        sp.edit().putString(SP_KEY_AUTH_TOKEN, loginResult?.authToken.orEmpty()).apply()
    }
}

sealed class AuthPass(val authorised: Boolean)

data class NotAuthorised(val auth: Boolean = false) : AuthPass(authorised = auth)