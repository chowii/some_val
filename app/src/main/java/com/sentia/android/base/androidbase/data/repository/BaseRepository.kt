package com.sentia.android.base.androidbase.data.repository

import android.arch.lifecycle.LiveData
import com.github.salomonbrys.kodein.LazyKodein
import com.sentia.android.base.androidbase.App
import com.sentia.android.base.androidbase.data.room.entity.SampleModel
import com.sentia.android.base.androidbase.util.Resource
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

    fun addSamples()

    fun getSampleList(): LiveData<Resource<List<SampleModel>>>

    fun getTotalSamples(): Flowable<Int>
}

data class SampleModel(val s1: String = "")