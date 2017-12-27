package com.sentia.android.base.androidbase.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by mariolopez on 27/12/17.
 */
data class SampleModels(@SerializedName("something") val s1: String,
                        @SerializedName("somekeyint") val int1: Int,
                        @SerializedName("double1") val double1: Int)