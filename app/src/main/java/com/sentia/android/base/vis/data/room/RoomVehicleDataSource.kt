package com.sentia.android.base.vis.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.sentia.android.base.vis.data.room.entity.*

/**
 * Created by mariolopez on 28/12/17.
 */
@Database(
        entities = [(Vehicle::class), (Tyre::class), (Servicing::class), (AddOns::class), (Others::class), (Photos::class)],
        version = 1)
abstract class RoomVehicleDataSource : RoomDatabase() {

    abstract fun vehiclesDao(): RoomVehiclesDao

    companion object {


        fun getAllVehicle() = listOf(
                Vehicle(123, "Ford", "Mondeo", "dunno", "dunno", "dunno", "dunno", "dunno", true, true, true, 123, 123, 123, 123312, 1203, 123123),
                Vehicle(1123, "Ford", "Mondeo1", "dunno", "dunno", "dunno", "dunno", "dunno", true, true, true, 123, 123, 123, 12413, 12734, 123123),
                Vehicle(2123, "Ford", "Mondeo2", "dunno", "dunno", "dunno", "dunno", "dunno", true, true, true, 123, 123, 123, 12835, 12413, 123123),
                Vehicle(4123, "Ford", "Mondeo3", "dunno", "dunno", "dunno", "dunno", "dunno", true, true, true, 123, 123, 123, 192523, 12233, 123123),
                Vehicle(123, "Ford", "Mondeo4", "dunno", "dunno", "dunno", "dunno", "dunno", true, true, true, 123, 123, 123, 12238, 1123, 123123),
                Vehicle(1253, "Ford", "Mondeo65", "dunno", "dunno", "dunno", "dunno", "dunno", true, true, true, 123, 123, 123, 12623, 2123, 123123),
                Vehicle(7123, "Ford", "Mondeo7", "dunno", "dunno", "dunno", "dunno", "dunno", true, true, true, 123, 123, 123, 129843, 1423, 123123),
                Vehicle(1283, "Ford", "Mondeo8", "dunno", "dunno", "dunno", "dunno", "dunno", true, true, true, 123, 123, 123, 12693, 17923, 123123),
                Vehicle(12343, "Ford", "Mondeo0", "dunno", "dunno", "dunno", "dunno", "dunno", true, true, true, 123, 123, 123, 121223, 1232, 123123))

        fun buildPersistentVehicle(context: Context): RoomVehicleDataSource = Room.databaseBuilder(
                context.applicationContext,
                RoomVehicleDataSource::class.java,
                RoomContract.DATABASE_VIS
        )
                //it's MANDATORY to write test for migrations https://developer.android.com/training/data-storage/room/migrating-db-versions.html
                //cause it can cause a crash loop
//                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()

    }

}