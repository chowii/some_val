package com.sentia.android.base.vis.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.sentia.android.base.vis.data.room.RoomContract.Companion.SELECT_FROM
import com.sentia.android.base.vis.data.room.RoomContract.Companion.SELECT_INSPECTIONS
import com.sentia.android.base.vis.data.room.RoomContract.Companion.SELECT_INSPECTIONS_COUNT
import com.sentia.android.base.vis.data.room.RoomContract.Companion.TABLE_INSPECTIONS
import com.sentia.android.base.vis.data.room.RoomContract.Companion.TABLE_PHOTOS
import com.sentia.android.base.vis.data.room.entity.Image
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.data.room.entity.Vehicle
import io.reactivex.Flowable

/**
 * Created by mariolopez on 28/12/17.
 */
@Dao
interface RoomInspectionDao {

    @Query(SELECT_INSPECTIONS)
    fun getAllInspecions(): LiveData<List<Inspection>>

    @Query(SELECT_INSPECTIONS_COUNT)
    fun getTotalInspections(): Flowable<Int>

    @Query(SELECT_FROM + TABLE_PHOTOS + " WHERE inspectionId IN (:inspectionId)")
    fun getPhotosVehicles(inspectionId: Long): Flowable<Image>

    //
    @Insert(onConflict = REPLACE)
    fun insertAll(vehicles: List<Inspection>)

    @Delete()
    fun delete(vehicle: Vehicle)

    @Query(SELECT_FROM + TABLE_INSPECTIONS + " WHERE id IN (:inspectionId)")
    fun getInspection(inspectionId: Long): LiveData<Inspection>

    @Query(SELECT_FROM + TABLE_INSPECTIONS + " WHERE id IN (:inspectionId)")
    fun getInspectionF(inspectionId: Long): Flowable<Inspection>

    @Query(SELECT_FROM + TABLE_PHOTOS +" WHERE inspectionId IS (:inspectionId)")
    fun getInspectionPhotos(inspectionId: Long): Flowable<List<Image>>
}
//note https://developer.android.com/training/data-storage/room/index.html