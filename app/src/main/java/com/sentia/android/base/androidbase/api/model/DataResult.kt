package com.sentia.android.base.androidbase.api.model

import com.google.gson.annotations.SerializedName
import com.sentia.android.base.androidbase.data.room.entity.SampleModel

/**
 * Created by mariolopez on 27/12/17.
 */
data class DataResult(@SerializedName("data") val data : SampleModel)