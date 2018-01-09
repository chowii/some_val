package com.sentia.android.base.vis.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.sentia.android.base.vis.data.room.RoomContract.Companion.SELECT_FROM
import com.sentia.android.base.vis.data.room.RoomContract.Companion.SELECT_VEHICLES
import com.sentia.android.base.vis.data.room.RoomContract.Companion.SELECT_VEHICLES_COUNT
import com.sentia.android.base.vis.data.room.RoomContract.Companion.TABLE_VEHICLES
import com.sentia.android.base.vis.data.room.RoomContract.Companion.TABLE_VEHICLES_PHOTOS
import com.sentia.android.base.vis.data.room.entity.Photos
import com.sentia.android.base.vis.data.room.entity.Vehicle
import io.reactivex.Flowable

/**
 * Created by mariolopez on 28/12/17.
 */
@Dao
interface RoomVehiclesDao {

    @Query(SELECT_VEHICLES)
    fun getAllVehicles(): LiveData<List<Vehicle>>

    @Query(SELECT_VEHICLES_COUNT)
    fun getTotalVehicles(): Flowable<Int>

    @Query(SELECT_FROM + TABLE_VEHICLES_PHOTOS + " WHERE vehicleId IN (:vehicleId)")
    fun getPhotosVehicles(vehicleId: Long): Flowable<Photos>

    //
    @Insert(onConflict = REPLACE)
    fun insertAll(vehicles: List<Vehicle>)

    @Delete()
    fun delete(vehicle: Vehicle)

    @Query(SELECT_FROM + TABLE_VEHICLES + " WHERE id IN (:vehicleId)")
    fun getVehicle(vehicleId: Long): LiveData<Vehicle>

}
//note https://developer.android.com/training/data-storage/room/index.html