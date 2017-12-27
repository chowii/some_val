package com.sentia.android.base.androidbase.util

/**
 * Created by mariolopez on 27/12/17.
 */
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBus {
    private val subject = PublishSubject.create<Any>()

    fun <E : Any> post(event: E) {
        subject.onNext(event)
    }

    fun <E : Any> observe(eventClass: Class<E>): Observable<E> {
        return subject.ofType(eventClass)
    }
    private object Holder {
        val INSTANCE = RxBus()
    }

    companion object Create {
        val instance: RxBus by lazy { Holder.INSTANCE }
    }
}