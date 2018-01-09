package com.sentia.android.base.vis.data.repository

import android.arch.lifecycle.LiveData
import com.github.salomonbrys.kodein.LazyKodein
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.data.room.entity.Vehicle
import com.sentia.android.base.vis.util.Resource
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by mariolopez on 27/12/17.
 */
abstract class BaseRepository : Repository {
    protected val kodein: LazyKodein = LazyKodein { App.context!!.kodein }
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
}

interface Repository {

    fun getSpecialSamples(): Flowable<Int>

    fun addMockedVehicles()

    fun getVehicles(): LiveData<Resource<List<Vehicle>>>

    fun findVehicle(id: Long): LiveData<Resource<Vehicle>>

    fun getTotalVehicles(): Flowable<Int>
}

data class SampleModel(val s1: String = "")