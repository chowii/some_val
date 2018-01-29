package com.sentia.android.base.vis.util.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoLogger


/**
 * Created by mariolopez on 23/1/18.
 */
class NetworkStatusManager(private val cm: ConnectivityManager) : BroadcastReceiver(), AnkoLogger {

    private val publisher = PublishSubject.create<NetworkStatus>()
    val obs = publisher.toFlowable(BackpressureStrategy.LATEST).toObservable()!!

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val status = getStatus()

            if (CONNECTIVITY_ACTION == intent?.action) {
                publisher.onNext(status)
            }
        }
    }

    private fun getStatus(): NetworkStatus {
        cm.activeNetworkInfo?.apply {
            return when (type) {
                TYPE_WIFI -> Wifi
                TYPE_MOBILE -> Mobile
                else -> NotConnected
            }
        }
        return NotConnected
    }
}

sealed class NetworkStatus
object Wifi : NetworkStatus()
object Mobile : NetworkStatus()
object NotConnected : NetworkStatus()