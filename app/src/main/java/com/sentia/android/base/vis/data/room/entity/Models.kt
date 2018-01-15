package com.sentia.android.base.vis.data.room.entity

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName
import com.sentia.android.base.vis.data.room.RoomContract

/**
 * Created by mariolopez on 27/12/17.
 */
@Entity(tableName = RoomContract.TABLE_INSPECTIONS,
        indices = [(Index(value = ["id"], unique = true))])
data class Inspection(
        @PrimaryKey
        @SerializedName("id") val id: Long,
        @SerializedName("state") val state: String,
        @SerializedName("master_key_and_remote") var masterKeyAndRemote: Boolean,
        @SerializedName("spare_key_and_remote") var spareKeyAndRemote: Boolean,
        @SerializedName("locking_wheel_nut") var lockingWheelNut: Boolean,
        @SerializedName("service_book") var serviceBook: Boolean,
        @SerializedName("service_required") var serviceRequired: Boolean,
        @SerializedName("owners_manual") var ownersManual: Boolean,
        @SerializedName("e_tag") var eTag: Boolean,
        @SerializedName("fuel_tag") var fuelTag: Boolean,
        @SerializedName("service_history") var serviceHistory: String?,
        @SerializedName("last_service_odometer") var odometer: String?,
        @SerializedName("last_service_date") var lastServiceDate: String?,
        @SerializedName("inspection_type") var inspectionType: String?,
        @SerializedName("condition") var condition: String?,
        @SerializedName("vehicle_condition") var vehicleCondition: String?,
        @Embedded
        @SerializedName("vehicle") var vehicle: Vehicle) {
    @Ignore
    @SerializedName("images")
    val images: MutableList<Image> = mutableListOf()
}

//uncomment and remove at will
//        see image for example


//        @SerializedName("depot") var rsfTyreId: Depot,

//        @SerializedName("exterior_damage_report") var extDamageReport: DamageReport,
//        @SerializedName("interior_damage_report") var intDamageReport: DamageReport,
//        @SerializedName("tyres") var tyres: List<Tyre>,
//        @SerializedName("accessories") var tyres: List<Accessory>,

//)

@Entity(tableName = RoomContract.TABLE_INSPECTION_DEPOT, foreignKeys = [
    ForeignKey(entity = Inspection::class, parentColumns = ["id"], childColumns = ["inspectionId"])])
data class Depot(
        @PrimaryKey
        @SerializedName("id") val id: Long,
        @SerializedName("name") val model: String,
        @SerializedName("inspection_id") val inspectionId: Long,
        @Embedded
        val locationDepot: LocationDepot)

@Entity(tableName = RoomContract.TABLE_PHOTOS, foreignKeys = [
    ForeignKey(entity = Inspection::class, parentColumns = ["id"], childColumns = ["inspectionId"])])
data class Image(
        @PrimaryKey
        @SerializedName("id") val id: Long,
        val inspectionId: Long,
        @SerializedName("name") val url: String?,
        @SerializedName("attachment") val attachmentB64: String,
        @SerializedName("overlay") val overlayB64: String)

@Entity(tableName = RoomContract.TABLE_DAMAGE_REPORT, foreignKeys = [
    ForeignKey(entity = Inspection::class, parentColumns = ["id"], childColumns = ["inspectionId"])])
data class DamageReport(
        @PrimaryKey
        @SerializedName("id") val idReport: Long,
        @SerializedName("inspection_id") val inspectionId: Long,
        @SerializedName("vehicle_template_id") val vehicleTemplateId: String,
        @SerializedName("is_inspected") val isInspected: Boolean) {
    @Ignore
    @SerializedName("items")
    val damageItems: List<DamageItem> = emptyList()
}

//)

@Entity(tableName = RoomContract.TABLE_DAMAGE_ITEM, foreignKeys = [
    ForeignKey(entity = Inspection::class, parentColumns = ["id"], childColumns = ["inspectionId"])])
data class DamageItem(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val inspectionId: Long,
        @SerializedName("component") val component: String?,
        @SerializedName("fault") val fault: String?,
        @SerializedName("repair_action") val repairAction: String?,
        @SerializedName("comment") val comment: String?,
        @SerializedName("coord_x") val coordX: Double?,
        @SerializedName("coord_y") val coordY: Double?,
        @SerializedName("is_inspected") val isInspected: Boolean) {
    @Ignore
    @SerializedName("images")
    val photos: MutableList<Image> = mutableListOf()
}

@Entity(tableName = RoomContract.TABLE_VEHICLES_TYRES, foreignKeys = [
    ForeignKey(entity = Inspection::class, parentColumns = ["id"], childColumns = ["inspectionId"])])
data class Tyre(
        val inspectionId: Long,
        @SerializedName("name") val model: String,
        @SerializedName("brand") val brand: String?,
        @SerializedName("depth") val depth: Double?,
        @PrimaryKey(autoGenerate = true) val idTyre: Long)

@Entity(tableName = RoomContract.TABLE_VEHICLES_ACCESSORIES, foreignKeys = [
    ForeignKey(entity = Inspection::class, parentColumns = ["id"], childColumns = ["inspectionId"])])
data class Accessory(
        @SerializedName("name") val name: String,
        @SerializedName("value") val isChecked: Boolean,
        @SerializedName("inspection_id") val inspectionId: Long,
        @PrimaryKey(autoGenerate = true) val id: Long)

data class LocationDepot(
        @SerializedName("id") val idLocation: Long,
        @SerializedName("address_1") val address1: String,
        @SerializedName("address_2") val address2: String?,
        @SerializedName("address_3") val address3: String?,
        @SerializedName("address_4") val address4: String,
        @SerializedName("postcode") val postCode: String,
        @SerializedName("suburb") val suburb: String,
        @SerializedName("state") val state: String,
        @SerializedName("country") val country: String,
        @SerializedName("lat") val latitude: Float?,
        @SerializedName("long") val longitude: Float?)