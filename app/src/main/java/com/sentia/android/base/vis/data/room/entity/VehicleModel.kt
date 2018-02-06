package com.sentia.android.base.vis.data.room.entity

import android.arch.persistence.room.ColumnInfo
import com.google.gson.annotations.SerializedName

/**
 * Created by mariolopez on 12/1/18.
 */
data class Vehicle(
        @ColumnInfo(name = "vehicle_id")
        @SerializedName("id") val idVehicle: Long,
        @SerializedName("drive_vehicle_id") var driveId: String? = null,
        @ColumnInfo(name = "vehicle_rego")
        @SerializedName("rego") var rego: String,
        @SerializedName("glasses_code") var glassesCode: String? = null,
        @ColumnInfo(name = "vehicle_vin")
        @SerializedName("vin") var vin: String,
        @SerializedName("make") var make: String,
        @SerializedName("model") var model: String,
        @SerializedName("derivative") var derivative: String? = null,
        @SerializedName("registered_date") var registeredDate: String? = null,
        @SerializedName("registered_state") var registeredState: String? = null,
        @SerializedName("registered_name") var registeredName: String? = null,
        @SerializedName("build_date") var buildDate: String? = null,
        @SerializedName("complianceDate") var complianceDate: String? = null,
        @SerializedName("vir_inspection_date") var virInspectionDate: String? = null,
        @SerializedName("registration_expiry_date") var registrationExpireDate: String? = null,
        @SerializedName("body_type") var bodyType: String? = null,
        @SerializedName("no_of_doors") var doors: Int? = null,
        @SerializedName("transmission_type") var transmissionType: String? = null,
        @SerializedName("fuel_type") var fuelType: String? = null,
        @SerializedName("emissions") var emissions: String? = null,
        @SerializedName("engine_size") var engineSize: String? = null,
        @SerializedName("engine_bhp") var engineBhp: String? = null,
        @SerializedName("drive_side") var driverSide: String? = null,
        @SerializedName("drive_transmission") var driverTransmission: String? = null,
        @SerializedName("drive_engineNumber") var engineNumber: String? = null,
        @SerializedName("gears") var gears: Int? = null,
        @SerializedName("colour") var colour: String? = null,
        @SerializedName("interior_trim") var interiorTrim: String? = null,
        @SerializedName("no_of_seats") var no_of_seats: Int? = null,
        @SerializedName("vat_qualifying") var vatQualifying: String? = null,
        @SerializedName("current_mileage") var miles: Int? = null,
        @SerializedName("date_of_current_mileage") var milesDate: String? = null,
        @SerializedName("p1") var p1: String? = null,
        @SerializedName("p2") var p2: String? = null,
        @SerializedName("vehicle_notes") var notes: String? = null,
//        @SerializedName("options") var options: String? = null,
        @SerializedName("instruction") var instruction: String? = null)
