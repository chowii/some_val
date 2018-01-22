package com.sentia.android.base.vis.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.sentia.android.base.vis.data.room.RoomContract.Companion.SELECT_FROM
import com.sentia.android.base.vis.data.room.RoomContract.Companion.SELECT_INSPECTIONS
import com.sentia.android.base.vis.data.room.RoomContract.Companion.SELECT_INSPECTIONS_COUNT
import com.sentia.android.base.vis.data.room.RoomContract.Companion.TABLE_INSPECTION_PHOTOS
import com.sentia.android.base.vis.data.room.RoomContract.Companion.TABLE_PHOTOS
import com.sentia.android.base.vis.data.room.entity.Image
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.data.room.entity.InspectionImage
import io.reactivex.Flowable

/**
 * Created by mariolopez on 28/12/17.
 */
@Dao
interface RoomInspectionDao {

    @Query(SELECT_INSPECTIONS)
    fun getAllInspections(): LiveData<List<Inspection>?>

    @Query(SELECT_INSPECTIONS + " WHERE vehicle_vin LIKE :search OR vehicle_rego LIKE :search")
    fun findInspection(search: String): LiveData<List<Inspection>>

    @Query(SELECT_INSPECTIONS_COUNT)
    fun getTotalInspections(): Flowable<Int>

    //
    @Insert(onConflict = REPLACE)
    fun insertAll(vehicles: List<Inspection>): List<Long>

    @Insert(onConflict = REPLACE)
    fun insertAllImages(photos: List<Image>): List<Long>

    @Insert(onConflict = REPLACE)
    fun insertAllInspectionImages(inspectionImage: List<InspectionImage>): List<Long>

    @Delete()
    fun delete(inspection: Inspection)

    @Query(SELECT_INSPECTIONS + " WHERE id IN (:inspectionId)")
    fun getInspectionF(inspectionId: Long): Flowable<Inspection>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query(SELECT_FROM + TABLE_PHOTOS + " INNER JOIN " + TABLE_INSPECTION_PHOTOS + " ON " + TABLE_PHOTOS + ".id IS " + TABLE_INSPECTION_PHOTOS + ".imageId"
            + " WHERE " + TABLE_INSPECTION_PHOTOS + ".inspectionId IS(:inspectionId)")
    fun getInspectionPhotos(inspectionId: Long): Flowable<List<Image>>
}
//note https://developer.android.com/training/data-storage/room/index.html