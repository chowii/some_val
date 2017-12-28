package com.sentia.android.base.androidbase.data.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sentia.android.base.androidbase.data.room.RoomContract

/**
 * Created by mariolopez on 27/12/17.
 */
@Entity(tableName = RoomContract.TABLE_SAMPLES)
data class SampleModel(
        @SerializedName("some_key_int") val int1: Int,
        @SerializedName("double1") val double1: Int,
        @SerializedName("something") val s1: String,
        @PrimaryKey(autoGenerate = true) val id: Long = 0)