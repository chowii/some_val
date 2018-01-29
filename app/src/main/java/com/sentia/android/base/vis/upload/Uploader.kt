package com.sentia.android.base.vis.upload

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.sentia.android.base.vis.data.InspectionRepository
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.util.connectivity.NetworkStatusManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by mariolopez on 22/1/18.
 */
class Uploader(private val context: Context, private val networkStatusManager: NetworkStatusManager,
               private val repo: InspectionRepository) : LifecycleObserver, AnkoLogger {

    private val compositeDisposable = CompositeDisposable()

    init {
        context.registerReceiver(networkStatusManager,
                IntentFilter().apply {
                    addAction(ConnectivityManager.CONNECTIVITY_ACTION)
                    addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
                })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun connectListener() {
        networkStatusManager.obs.subscribeBy(
                onNext = {
//                    sync()
                    info { "received: " + it::class.java.simpleName }
                })
    }

    fun sync(inspectionToUpload: Inspection? = null) = repo.sync(inspectionToUpload)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun disconnectListener() {
        compositeDisposable.clear()
    }

}