package com.sentia.android.base.androidbase.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.sentia.android.base.androidbase.data.room.entity.SampleModel
import io.reactivex.Flowable

/**
 * Created by mariolopez on 28/12/17.
 */
@Dao
interface RoomSamplesDao {

    @Query(RoomContract.SELECT_SAMPLES)
    fun getAllSamples(): Flowable<List<SampleModel>>

    @Query(RoomContract.SELECT_SAMPLES_COUNT)
    fun getTotalSamples(): Flowable<Int>

    @Insert()
    fun insertAll(samples: List<SampleModel>)
}