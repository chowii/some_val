package com.sentia.android.base.vis.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.Flowable

/**
 * Created by mariolopez on 24/1/18.
 */
fun <T> Flowable<T>.toLiveData(): LiveData<T?> {
    return LiveDataReactiveStreams.fromPublisher(this)
}