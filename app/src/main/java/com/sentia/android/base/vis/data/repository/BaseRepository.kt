package com.sentia.android.base.vis.data.repository

import android.arch.lifecycle.LiveData
import com.github.salomonbrys.kodein.LazyKodein
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.util.Resource
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger

/**
 * Created by mariolopez on 27/12/17.
 */
abstract class BaseRepository : Repository, AnkoLogger {
    protected val kodein: LazyKodein = LazyKodein { App.context!!.kodein }
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
}

interface Repository {

    fun getInspections(): LiveData<Resource<List<Inspection>>>

    fun findCompleteInspection(inspectionId: Long): Flowable<Resource<Inspection>>

    fun getTotalInspections(): Flowable<Int>
    fun getTotalLookups(): Flowable<Int>
    fun getTotalDepots(): Flowable<Int>
}
