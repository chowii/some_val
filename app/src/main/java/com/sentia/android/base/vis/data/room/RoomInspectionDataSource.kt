package com.sentia.android.base.vis.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.sentia.android.base.vis.data.room.entity.*
import com.sentia.android.base.vis.util.TestDataProvider

/**
 * Created by mariolopez on 28/12/17.
 */
@Database(entities = [(Inspection::class), (Depot::class), (Image::class), (DamageReport::class),
    (Accessory::class), (Tyre::class), (DamageItem::class)], version = 1,exportSchema = true)
abstract class RoomInspectionDataSource : RoomDatabase() {

    abstract fun inspectionDao(): RoomInspectionDao

    companion object {

        fun getAllInspections() = TestDataProvider.getInspections()

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