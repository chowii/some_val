package com.sentia.android.base.vis.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.data.remote.RemoteDataSource
import com.sentia.android.base.vis.data.repository.BaseRepository
import com.sentia.android.base.vis.data.room.RoomVehicleDataSource
import com.sentia.android.base.vis.data.room.entity.Vehicle
import com.sentia.android.base.vis.util.Resource
import com.sentia.android.base.vis.util.Resource.Status.*
import com.sentia.android.base.vis.util.exception.AppException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers


/**
 * Created by mariolopez on 27/12/17.
 */
class VehicleRepository : BaseRepository() {

    private val remoteDataSource by kodein.instance<RemoteDataSource>()
    private val roomVehicleDataSource by kodein.instance<RoomVehicleDataSource>()

    override fun getSpecialSamples(): Flowable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addMockedVehicles() {
        val vehicles = RoomVehicleDataSource.getAllVehicle()
        roomVehicleDataSource.vehiclesDao().insertAll(vehicles)
    }

    private fun addVehicles(vehicles: List<Vehicle>) {
        roomVehicleDataSource.vehiclesDao().insertAll(vehicles)

    }

    override fun findVehicle(id: Long): LiveData<Resource<Vehicle>> {
        return Transformations.map(roomVehicleDataSource.vehiclesDao().getVehicle(id),
                { vehicle: Vehicle? -> Resource(SUCCESS, vehicle) })
    }

    override fun getVehicles(): LiveData<Resource<List<Vehicle>>> {
        val result = MediatorLiveData<Vehicle>()
        val liveResult = MutableLiveData<Resource<List<Vehicle>>>()
        val resultLiveAndRoom = MutableLiveData<Resource<List<Vehicle>>>()

        liveResult.value = Resource(LOADING)

        //todo this logic needs to be refactored once we know how the api will work
        //todo at the current moment we just add both sources so the loading feeding won't be effective 100%

        result.addSource(roomVehicleDataSource.vehiclesDao().getAllVehicles(),
                { vehicles: List<Vehicle>? -> resultLiveAndRoom.value = Resource(SUCCESS, vehicles) })
        result.addSource(liveResult,
                { vehiclesResult: Resource<List<Vehicle>>? -> resultLiveAndRoom.value = vehiclesResult })

        compositeDisposable += remoteDataSource.getVehicleList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            addVehicles(it)
                            liveResult.value = Resource(SUCCESS, it)
                        },
                        { liveResult.value = Resource(ERROR, null, AppException(it)) })
        return resultLiveAndRoom
    }

    override fun getTotalVehicles() = roomVehicleDataSource.vehiclesDao().getTotalVehicles()

    fun doSearch(search: String?): LiveData<Resource<List<Vehicle>>> {
        // todo have logic here to get data from db or/and online then filter the list
        return MutableLiveData()
    }


}