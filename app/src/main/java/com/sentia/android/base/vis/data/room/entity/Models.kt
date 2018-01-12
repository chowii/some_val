package com.sentia.android.base.vis.data.room.entity

import android.arch.persistence.room.*
import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import com.sentia.android.base.vis.data.room.RoomContract

/**
 * Created by mariolopez on 27/12/17.
 */
@Entity(tableName = RoomContract.TABLE_VEHICLES,
        indices = [(Index(value = ["id", "model"], unique = true)), (Index(value = ["model", "make"], unique = true))])
data class Vehicle(
        @PrimaryKey
        @SerializedName("some_key") val id: Long,
        @SerializedName("some_key") val make: String,
        @SerializedName("some_key") val model: String,
        @SerializedName("some_key") var derivative: String,
        @SerializedName("some_key") var registration: String,
        @SerializedName("some_key") var registrationState: String,
        @SerializedName("some_key") var vin: String,
        @SerializedName("some_key") var odometer: String,
        @SerializedName("some_key") var masterKeyAndRemote: Boolean,
        @SerializedName("some_key") var spareKeyAndRemote: Boolean,
        @SerializedName("some_key") var lockingWheelNut: Boolean,
        @SerializedName("some_key") var lsfTyreId: Int,
        @SerializedName("some_key") var rsfTyreId: Int,
        //tyres
        @SerializedName("some_key") var rfrTyreId: Int, //left side front
        @SerializedName("some_key") var lfrTyreId: Int, //right side front
        @SerializedName("some_key") var spareTyreId: Int, //right side rear
        @SerializedName("some_key") var servicingId: Int)

@Entity(tableName = RoomContract.TABLE_VEHICLES_TYRES,
        foreignKeys = [(ForeignKey(entity = Vehicle::class,
                parentColumns = ["id"], childColumns = ["vehicleId"]))])
data class Tyre(
        @SerializedName("some_key") val depth: String,
        @SerializedName("some_key") val model: String,
        @SerializedName("some_key") val vehicleId: Long,
        @PrimaryKey(autoGenerate = true) val id: Long)

@Entity(tableName = RoomContract.TABLE_VEHICLES_SERVICING,
        foreignKeys = [(ForeignKey(entity = Vehicle::class,
                parentColumns = ["id"], childColumns = ["vehicleId"]))])
data class Servicing(
        @SerializedName("some_key") val vehicleId: Long,
        @SerializedName("some_key") val serviceBook: Boolean,
        @SerializedName("some_key") val ownersManual: Boolean,
        @SerializedName("some_key") val serviceRequire: Boolean,
        @SerializedName("some_key") val eTag: Boolean,
        @SerializedName("some_key") val fuelTag: Boolean,
        @SerializedName("some_key") val serviceHistory: String,
        @SerializedName("some_key") val lastServiceOdometer: String,
        @SerializedName("some_key") val lastServiceDate: String,
        @PrimaryKey(autoGenerate = true) val id: Long)

@Entity(tableName = RoomContract.TABLE_VEHICLES_ADD_ONS,
        foreignKeys = [(ForeignKey(entity = Vehicle::class,
                parentColumns = ["id"], childColumns = ["vehicleId"]))])
data class AddOns(
        @SerializedName("some_key") val vehicleId: Long,
        @SerializedName("some_key") val satelliteNavigation: Boolean,
        @SerializedName("some_key") val reverseCamera: Boolean,
        @SerializedName("some_key") val frontParkingSensors: Boolean,
        @SerializedName("some_key") val rearParkingSensors: Boolean,
        @SerializedName("some_key") val autoPark: Boolean,
        @SerializedName("some_key") val towBar: Boolean,
        @SerializedName("some_key") val bullBar: Boolean,
        @SerializedName("some_key") val sunRoof: Boolean,
        @SerializedName("some_key") val roofRack: Boolean,
        @SerializedName("some_key") val leather: Boolean,
        @SerializedName("some_key") val serviceBody: Boolean,
        @PrimaryKey(autoGenerate = true) val id: Long)

@Entity(tableName = RoomContract.TABLE_VEHICLES_OTHERS,
        foreignKeys = [(ForeignKey(entity = Vehicle::class,
                parentColumns = ["id"], childColumns = ["vehicleId"]))])
data class Others(
        @SerializedName("some_key") val vehicleId: Long,
        @SerializedName("some_key") val location: String,
        @SerializedName("some_key") val address: String,
//        @ForeignKey() //todo
        @SerializedName("some_key") val inspectionTypeId: Int,
//        @ForeignKey()//todo
        @SerializedName("some_key") val vehicleConditionId: Int,
//        @ForeignKey()//todo
        @SerializedName("some_key") val inspectionConditionId: Int,
        @PrimaryKey(autoGenerate = true) val id: Long)

@Entity(tableName = RoomContract.TABLE_VEHICLES_PHOTOS,
        foreignKeys = [(ForeignKey(entity = Vehicle::class,
                parentColumns = ["id"], childColumns = ["vehicleId"]))])
data class Photos(
        @SerializedName("some_key") val vehicleId: Long,
        @SerializedName("some_key") val url: String,
        @SerializedName("some_key") val base64: String,
        @PrimaryKey(autoGenerate = true) val id: Long
        /*@Ignore val bitmap: Bitmap */) {
    @Ignore
    val bitmap: Bitmap? = null
//    @Ignore constructor(vehicleId: Long = 0, url: String = "", base64: String= "",bitmap: Bitmap?=null) : this()
}