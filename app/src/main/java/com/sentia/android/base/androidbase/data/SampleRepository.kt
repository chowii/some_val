package com.sentia.android.base.androidbase.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.androidbase.data.remote.RemoteDataSource
import com.sentia.android.base.androidbase.data.repository.BaseRepository
import com.sentia.android.base.androidbase.data.room.RoomSampleDataSource
import com.sentia.android.base.androidbase.data.room.entity.SampleModel
import com.sentia.android.base.androidbase.util.Resource
import com.sentia.android.base.androidbase.util.Resource.Status.ERROR
import com.sentia.android.base.androidbase.util.Resource.Status.SUCCESS
import com.sentia.android.base.androidbase.util.exception.AppException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

/**
 * Created by mariolopez on 27/12/17.
 */
class SampleRepository : BaseRepository() {

    private val remoteDataSource by kodein.instance<RemoteDataSource>()
    private val roomSampleDataSource by kodein.instance<RoomSampleDataSource>()

    override fun getSpecialSamples(): Flowable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSamples() {
        val currencyEntityList = RoomSampleDataSource.getAllSamples()
        roomSampleDataSource.samplesDao().insertAll(currencyEntityList)
    }

    override fun getSampleList(): LiveData<Resource<List<SampleModel>>> {
        val mutableLiveSample = MutableLiveData<Resource<List<SampleModel>>>()

        compositeDisposable += remoteDataSource.getSampleList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { mutableLiveSample.value = Resource(SUCCESS, it) },
                        { mutableLiveSample.value = Resource(ERROR, null, AppException(it)) })
        return mutableLiveSample
    }

    override fun getTotalSamples() = roomSampleDataSource.samplesDao().getTotalSamples()


}