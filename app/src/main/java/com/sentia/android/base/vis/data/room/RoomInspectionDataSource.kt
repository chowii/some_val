package com.sentia.android.base.vis.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.sentia.android.base.vis.data.room.entity.*

/**
 * Created by mariolopez on 28/12/17.
 */
@Database(entities = [(Inspection::class), (Depot::class), (Image::class), (DamageReport::class),
            (Accessory::class), (Vehicle::class), (Tyre::class), (DamageItem::class)], version = 1)
abstract class RoomInspectionDataSource : RoomDatabase() {

    abstract fun inspectionDao(): RoomInspectionDao

    companion object {


        //        fun getAllInspections() = listOf(
//                Inspection(123, "NSW", false, false, false, false, true, false, "etag", "fueltag", "noIDEA", "randomValue", "someDate", "waxon_waxoff", "mint", "noidea2", arrayListOf()),
//                Inspection(12, "QSL", true, true, true, true, true, true, "etag2", "fueltag2", "noIDEA2", "randomValue2", "someDate2", "waxon_waxoff2", "filthy", "noidea2", arrayListOf()))
        fun getAllInspections() = listOf(
                Inspection(123, "NSW", false, false, false, false, true, false, false, false, "noIDEA", "randomValue", "someDate", "waxon_waxoff", "mint", "noidea2"),
                Inspection(12, "QSL", true, true, true, true, true, true, true, true, "noIDEA2", "randomValue2", "someDate2", "waxon_waxoff2", "filthy", "noidea2"))


        fun buildPersistentVehicle(context: Context): RoomInspectionDataSource = Room.databaseBuilder(
                context.applicationContext,
                RoomInspectionDataSource::class.java,
                RoomContract.DATABASE_VIS
        )
                //it's MANDATORY to write test for migrations https://developer.android.com/training/data-storage/room/migrating-db-versions.html
                //cause it can cause a crash loop
//                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()

    }

}