package com.sentia.android.base.vis.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by mariolopez on 16/1/18.
 */
data class LoginResult(@SerializedName("access_token")
                       val authToken: String)