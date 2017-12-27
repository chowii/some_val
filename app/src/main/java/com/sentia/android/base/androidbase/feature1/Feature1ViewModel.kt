package com.sentia.android.base.androidbase.feature1

/**
 * Created by mariolopez on 27/12/17.
 */
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.androidbase.App
import com.sentia.android.base.androidbase.api.model.SampleModel
import com.sentia.android.base.androidbase.base.BaseViewModel
import com.sentia.android.base.androidbase.data.SampleRepository
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Feature1ViewModel : BaseViewModel() {

    override val repository by kodein.instance<SampleRepository>()

    private var liveSampleData: LiveData<List<SampleModel>>? = null



    fun loadSampleList(): LiveData<List<SampleModel>>? {
        if (liveSampleData == null) {
            liveSampleData = MutableLiveData<List<SampleModel>>()
            liveSampleData = repository.getSampleList()
        }
        return liveSampleData
    }

    fun initLocalCurrencies() {
        val disposable = currencyRepository.getTotalCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isRoomEmpty(it)) {
                        populate()
                    } else {
                        Log.i(CurrencyRepository::class.java.simpleName, "DataSource has been already Populated")
                    }
                }
        compositeDisposable.add(disposable)
    }

    private fun isRoomEmpty(currenciesTotal: Int) = currenciesTotal == 0

    private fun populate() {
        Completable.fromAction { currencyRepository.addCurrencies() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {
                        Log.i(CurrencyRepository::class.java.simpleName, "DataSource has been Populated")

                    }

                    override fun onError(@NonNull e: Throwable) {
                        e.printStackTrace()
                        Log.e(CurrencyRepository::class.java.simpleName, "DataSource hasn't been Populated")
                    }
                })
    }

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }

//    fun getAvailableExchange(currencies: String): LiveData<AvailableExchange>? {
//        liveAvailableExchange = null
//        liveAvailableExchange = MutableLiveData<AvailableExchange>()
//        liveAvailableExchange = currencyRepository.getAvailableExchange(currencies)
//        return liveAvailableExchange
//    }


}


