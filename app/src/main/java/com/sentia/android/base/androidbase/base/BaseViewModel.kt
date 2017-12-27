package com.sentia.android.base.androidbase.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import com.github.salomonbrys.kodein.LazyKodein
import com.sentia.android.base.androidbase.App
import com.sentia.android.base.androidbase.data.repository.BaseRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by mariolopez on 27/12/17.
 */
abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    protected val kodein: LazyKodein = LazyKodein { App.context!!.kodein }
    private val compositeDisposable = CompositeDisposable()
    abstract val repository: BaseRepository

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun unSubscribeViewModel() {
        repository.compositeDisposable.clear()
        compositeDisposable.clear()
    }

}