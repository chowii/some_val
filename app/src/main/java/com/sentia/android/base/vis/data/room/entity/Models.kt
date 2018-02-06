package com.sentia.android.base.vis.data.room.entity

import android.arch.persistence.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sentia.android.base.vis.data.room.RoomContract
import com.sentia.android.base.vis.data.room.entity.UploadStatus.Status.SYNCED
import com.sentia.android.base.vis.util.TestDataProvider

/**
 * Created by mariolopez on 27/12/17.
 */
@Entity(tableName = RoomContract.TABLE_INSPECTIONS,
        indices = [(Index(value = ["id"], unique = true)),
            (Index(value = ["vehicle_id"])),
            (Index(value = ["vehicle_vin"]))])
data class Inspection(
        @PrimaryKey
        @SerializedName("id") var id: Long,
        @SerializedName("state") var state: String,
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
        @SerializedName("accessories") var accessories: MutableList<Accessory>,
        @Embedded
        @SerializedName("vehicle") var vehicle: Vehicle,
        @Expose(serialize = false, deserialize = false)
        @Embedded
        var uploadStatus: UploadStatus) {

    constructor() : this(0, "", false, false,
            false, false, false, false, false,
            false, null, null, null, null,
            null, null, mutableListOf<Accessory>(),
            TestDataProvider.createRandomVehicle(), UploadStatus())

    @Ignore
    @SerializedName("images")
    val images: MutableList<Image> = mutableListOf()

    @Ignore
    @Expose(serialize = false, deserialize = false)
    var inspectedDate: String = ""

    fun copyWithList(): Inspection {
        val copy = copy()
        copy.images.addAll(this.images)
        return copy
    }

//uncomment and remove at will see image for example

//        @SerializedName("depot") var rsfTyreId: Depot,
//        @SerializedName("exterior_damage_report") var extDamageReport: DamageReport,
//        @SerializedName("interior_damage_report") var intDamageReport: DamageReport,
//        @SerializedName("tyres") var tyres: List<Tyre>,,

//)
}

@Entity(tableName = RoomContract.TABLE_PHOTOS,
        indices = [(Index(value = ["id"], unique = true))])
data class Image(
        @PrimaryKey
        @SerializedName("id") val id: Long,
        var synced: Boolean = true,
        @SerializedName("name") val name: String?,
        @SerializedName("is_attachment_present") val isAttachmentUploaded: Boolean,
        @SerializedName("is_overlay_present") val isOverlayUploaded: Boolean,
        @SerializedName("attachment") var attachmentB64: String?,
        @SerializedName("overlay") val overlayB64: String?)

@Entity(tableName = RoomContract.TABLE_DAMAGE_REPORT, foreignKeys = [
    ForeignKey(entity = Inspection::class, parentColumns = ["id"], childColumns = ["inspectionId"])],
        indices = [(Index(value = ["inspectionId"], unique = true))])
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
    ForeignKey(entity = Inspection::class, parentColumns = ["id"], childColumns = ["inspectionId"])],
        indices = [(Index(value = ["inspectionId"], unique = true))])
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
    ForeignKey(entity = Inspection::class, parentColumns = ["id"], childColumns = ["inspectionId"])],
        indices = [(Index(value = ["inspectionId"], unique = true))])
data class Tyre(
        val inspectionId: Long,
        @SerializedName("name") val model: String,
        @SerializedName("brand") val brand: String?,
        @SerializedName("depth") val depth: Double?,
        @PrimaryKey(autoGenerate = true) val idTyre: Long)

data class Accessory(
        @SerializedName("name") var name: String,
        @SerializedName("value") var isChecked: Boolean)

@SuppressWarnings(RoomWarnings.MISSING_INDEX_ON_FOREIGN_KEY_CHILD)
@Entity(tableName = RoomContract.TABLE_INSPECTION_PHOTOS, primaryKeys = [
    "inspectionId", "imageId"], foreignKeys = [
    ForeignKey(entity = Inspection::class, parentColumns = ["id"], childColumns = ["inspectionId"],
            onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Image::class, parentColumns = ["id"], childColumns = ["imageId"],
            onDelete = ForeignKey.CASCADE)],
        indices = [(Index(value = ["inspectionId"]))])
data class InspectionImage(
        val inspectionId: Long,
        val imageId: Long)

data class UploadStatus(
        @ColumnInfo(name = "status")
        var status: Status = SYNCED, var uploadDate: String? = null) {
    constructor() : this(SYNCED, null)

    enum class Status {
        SYNCED,
        NOT_SYNCED,
        UPLOADING,
        FAILED
    }
}

@Entity(tableName = RoomContract.TABLE_LOOKUPS)
data class Lookups(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @SerializedName("tyre_brand")
        val tyreBrands: List<LookupItem>,
        @SerializedName("spare_tyre")
        val spareTyreBrands: List<LookupItem>,
        @SerializedName("service_history")
        val serviceHistories: List<LookupItem>,
        @SerializedName("inspection_condition")
        val inspectionConditions: List<LookupItem>,
        @SerializedName("vehicle_condition")
        val vehicleConditions: List<LookupItem>)

data class LookupItem(val name: String)

@Entity(tableName = RoomContract.TABLE_INSPECTION_DEPOT,
        indices = [(Index(value = ["id"]))])
data class Depot(
        @PrimaryKey()
        val id: Int,
        @SerializedName("company_name")
        val companyName: String,
        @Embedded
        @SerializedName("location")
        val location: LocationDepot)

data class LocationDepot(
        @SerializedName("id") val idLocation: Long,
        @SerializedName("address_1") val address1: String?,
        @SerializedName("address_2") val address2: String?,
        @SerializedName("address_3") val address3: String?,
        @SerializedName("address_4") val address4: String?,
        @SerializedName("postcode") val postCode: String?,
        @SerializedName("suburb") val suburb: String?,
        @SerializedName("state") val state: String?,
        @SerializedName("country") val country: String?,
        @SerializedName("lat") val latitude: Double?,
        @SerializedName("long") val longitude: Double?) {


    val fullAddress: String
        @Ignore
        get() = "${address1.orEmpty()} ${address2.orEmpty()} ${address3.orEmpty()} ${address4.orEmpty()}".trim()

}
