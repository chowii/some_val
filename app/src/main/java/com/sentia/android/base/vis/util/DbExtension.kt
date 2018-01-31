package com.sentia.android.base.vis.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.Flowable

/**
 * Created by mariolopez on 24/1/18.
 */
fun <T> Flowable<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this)
}

fun CharSequence.toRoomSearchString(): String = this.toString().toRoomSearchString()
fun String.toRoomSearchString() = "%$this%" /* by putting % the search works like contains*/
fun String.isNotABlankSearchString() = this.replace("%","").isNotBlank()