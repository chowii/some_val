package com.sentia.android.base.vis.data.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sentia.android.base.vis.data.room.RoomContract

/**
 * Created by mariolopez on 12/1/18.
 */
@Entity(tableName = RoomContract.TABLE_VEHICLES,
        indices = [(Index(value = ["id", "model"], unique = true)), (Index(value = ["model", "make"], unique = true))])
data class Vehicle(
        @PrimaryKey
        @SerializedName("id") val id: Long,
        @SerializedName("inspection_id") val inspectionId: Long,
        @SerializedName("drive_vehicle_id") val driveId: String?,
        @SerializedName("rego") val rego: String,
        @SerializedName("glasses_code") val glassesCode: String?,
        @SerializedName("vin") var vin: String,
        @SerializedName("make") val make: String,
        @SerializedName("model") val model: String,
        @SerializedName("derivative") var derivative: String?,
        @SerializedName("registered_date") val registeredDate: String?,
        @SerializedName("registered_state") val registeredState: String?,
        @SerializedName("registered_name") val registeredName: String?,
        @SerializedName("buildDate") var buildDate: String?,
        @SerializedName("complianceDate") var complianceDate: String?,
        @SerializedName("vir_inspection_date") var virInspectionDate: String?,
        @SerializedName("registration_expiry_date") var registrationExpireDate: String?,
        @SerializedName("body_type") var bodyType: String?,
        @SerializedName("no_of_doors") var doors: Int?,
        @SerializedName("transmissionType") var transmissionType: String?,
        @SerializedName("fuel_type") var fuelType: String?,
        @SerializedName("emissions") var emissions: String?,
        @SerializedName("engine_size") var engineSize: String?,
        @SerializedName("engine_bhp") var engineBhp: String?,
        @SerializedName("drive_side") var driverSide: String?,
        @SerializedName("drive_transmission") var driverTransmission: String?,
        @SerializedName("drive_engineNumber") var engineNumber: String?,
        @SerializedName("gears") var gears: Int?,
        @SerializedName("colour") var colour: String?,
        @SerializedName("interior_trim") var interiorTrim: String?,
        @SerializedName("no_of_seats") var no_of_seats: Int?,
        @SerializedName("vat_qualifying") var vatQualifying: String?,
        @SerializedName("current_mileage") var miles: Int?,
        @SerializedName("date_of_current_mileage") var milesDate: String?,
        @SerializedName("p1") var p1: String?,
        @SerializedName("p2") var p2: String?,
        @SerializedName("vehicle_notes") var notes: String?,
        @SerializedName("options") var options: String?,
        @SerializedName("instruction") var instruction: String?)
