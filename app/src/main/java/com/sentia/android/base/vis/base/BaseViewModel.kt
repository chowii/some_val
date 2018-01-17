package com.sentia.android.base.vis.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import com.github.salomonbrys.kodein.LazyKodein
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.data.repository.BaseRepository
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger

/**
 * Created by mariolopez on 27/12/17.
 */
abstract class BaseViewModel : ViewModel(), LifecycleObserver, AnkoLogger {

    protected val kodein: LazyKodein = LazyKodein { App.context!!.kodein }
    protected val compositeDisposable = CompositeDisposable()
    abstract val repository: BaseRepository

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun unSubscribeViewModel() {
        repository.compositeDisposable.clear()
        compositeDisposable.clear()
    }

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }

}