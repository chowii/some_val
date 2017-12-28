package com.sentia.android.base.androidbase.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.sentia.android.base.androidbase.data.room.entity.SampleModel

/**
 * Created by mariolopez on 28/12/17.
 */
@Database(
        entities = arrayOf(SampleModel::class),
        version = 1)
abstract class RoomSampleDataSource : RoomDatabase() {

    abstract fun samplesDao(): RoomSamplesDao

    companion object {

        fun buildPersistentSamples(context: Context): RoomSampleDataSource = Room.databaseBuilder(
                context.applicationContext,
                RoomSampleDataSource::class.java,
                RoomContract.DATABASE_SAMPLE
        ).build()

        fun getAllSamples() = listOf(
                SampleModel(32, 1263, "mario"),
                SampleModel(12, 15623, "jovan"),
                SampleModel(33, 12353, "julia"),
                SampleModel(29, 12333, "sabibb"),
                SampleModel(11, 1223, "sam"),
                SampleModel(10, 12213, "zhang"),
                SampleModel(44, 1213, "tom"),
                SampleModel(55, 17213, "micheal"))

    }
}