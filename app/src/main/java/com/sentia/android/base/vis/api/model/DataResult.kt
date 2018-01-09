package com.sentia.android.base.vis.api.model

import com.google.gson.annotations.SerializedName
import com.sentia.android.base.vis.data.room.entity.Vehicle

/**
 * Created by mariolopez on 27/12/17.
 */
data class DataResult(@SerializedName("data") val data : Vehicle)