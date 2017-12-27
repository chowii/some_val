package com.sentia.android.base.androidbase.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.androidbase.api.RestAdapter
import com.sentia.android.base.androidbase.api.model.SampleModel
import com.sentia.android.base.androidbase.data.repository.BaseRepository
import io.reactivex.Flowable

/**
 * Created by mariolopez on 27/12/17.
 */
class SampleRepository : BaseRepository() {
    private val restAdapter by kodein.instance<RestAdapter>()

    override fun getSpecialSamples(): Flowable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSample() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSampleList(): LiveData<List<SampleModel>> {
        val mutableLiveSample: MutableLiveData<SampleModel>

        restAdapter.getSampleList()
    }

}