package com.sentia.android.base.androidbase.feature1

/**
 * Created by mariolopez on 27/12/17.
 */
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.androidbase.base.BaseViewModel
import com.sentia.android.base.androidbase.data.SampleRepository
import com.sentia.android.base.androidbase.data.room.entity.SampleModel
import com.sentia.android.base.androidbase.util.Resource
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info

class Feature1ViewModel : BaseViewModel(), AnkoLogger {

    override val repository by kodein.instance<SampleRepository>()
    private var liveSampleData: LiveData<Resource<List<SampleModel>>>? = null


    fun loadSampleList(): LiveData<Resource<List<SampleModel>>>? {
        if (liveSampleData == null) {
            liveSampleData = MutableLiveData<Resource<List<SampleModel>>>()
            liveSampleData = repository.getSampleList()
        }
        return liveSampleData
    }

    fun initLocalSamples() {
        compositeDisposable += repository.getTotalSamples()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isRoomEmpty(it)) {
                        populate() //mocked version for data we could switch map and fetch for remote repository
                    } else info("DataSource has been already Populated")

                }
    }

    private fun isRoomEmpty(storedSamplesTotal: Int) = storedSamplesTotal == 0

    private fun populate() {
        Completable.fromAction { repository.addSamples() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        compositeDisposable += d
                    }

                    override fun onComplete() {
                        info("DataSource has been Populated")
                    }

                    override fun onError(@NonNull e: Throwable) {
                        error("DataSource hasn't been Populated")
                    }
                })
    }
}


