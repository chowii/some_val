package com.sentia.android.base.androidbase.data.room

/**
 * Created by mariolopez on 28/12/17.
 */
class RoomContract {

    companion object {

        const val DATABASE_SAMPLE = "Samples.db"

        const val TABLE_SAMPLES = "Sample_Model_Table"

        private const val SELECT_COUNT = "SELECT COUNT(*) FROM "
        private const val SELECT_FROM = "SELECT * FROM "

        const val SELECT_SAMPLES_COUNT = SELECT_COUNT + TABLE_SAMPLES
        const val SELECT_SAMPLES = SELECT_FROM + TABLE_SAMPLES

    }
}