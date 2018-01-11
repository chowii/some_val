package com.sentia.android.base.vis.evaluation.inspection

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.base.BaseViewModel
import com.sentia.android.base.vis.data.VehicleRepository
import com.sentia.android.base.vis.data.room.entity.Vehicle
import com.sentia.android.base.vis.util.Resource
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.error
import org.jetbrains.anko.info

/**
 * Created by mariolopez on 9/1/18.
 */
class InspectionViewModel : BaseViewModel() {
    override val repository by kodein.instance<VehicleRepository>()
    private var liveVehicleData: LiveData<Resource<Vehicle>>? = null
    private val vehicle: MutableLiveData<Long> = MutableLiveData()

    val vehicleResult = Transformations.switchMap(vehicle) {
        //this is lazy load so it caches the result on rotation
        repository.findVehicle(it)
    }

    fun findVehicle(id: Long) {
        vehicle.value = id
    }

    //todo remove the following 3 methods
    fun initLocalVehicles() {
        compositeDisposable += repository.getTotalVehicles()
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
        Completable.fromAction { repository.addMockedVehicles() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(@NonNull disposable: Disposable) {
                        compositeDisposable += disposable
                    }

                    override fun onComplete() {
                        info("DataSource has been Populated")
                    }

                    override fun onError(@NonNull e: Throwable) {
                        error("DataSource hasn't been Populated yet")
                    }
                })
    }

}