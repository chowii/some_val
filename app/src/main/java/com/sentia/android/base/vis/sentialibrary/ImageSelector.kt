package com.sentia.android.base.vis.sentialibrary

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.net.Uri
import android.os.Build
import com.marchinram.rxgallery.RxGallery
import com.sentia.android.base.vis.R
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.selector

class ImageSelector(private val activity: Activity) : LifecycleObserver, AnkoLogger {
    private val imageSelectionSubject: PublishSubject<Uri> = PublishSubject.create()

    private val rxGallery = RxGallery.gallery(activity, false, RxGallery.MimeType.IMAGE)
    private val rxCamera = RxGallery.photoCapture(activity)

    private var galleryDisposable: Disposable? = null
    private var cameraDisposable: Disposable? = null

    fun selectImage(): Observable<Uri> {
        showPhotoSelectorDialog()
        return imageSelectionSubject.toFlowable(BackpressureStrategy.MISSING).toObservable()
    }

    private fun showPhotoSelectorDialog() {
        val options = listOf(
                activity.getString(R.string.photos_dialog_camera),
                activity.getString(R.string.photos_dialog_gallery))

        activity.selector(null, options, { _, i ->
            when (i) {
                0 -> capturePhoto()
                1 -> selectPhotoFromGallery()
            }
        })
    }

    private fun selectPhotoFromGallery() {
        clear()
        galleryDisposable = rxGallery.toObservable().subscribe(
                {
                    val first = it.first { it != null }
                    imageSelectionSubject.onNext(first)
                    imageSelectionSubject.onComplete()
                },
                {
                    imageSelectionSubject.onError(it)
                    error { "Error during selecting image from gallery" }
                })
    }

    private fun capturePhoto() {
        clear()
        var permissionObservable = Observable.just(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionObservable = RxPermissions(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        cameraDisposable = permissionObservable.flatMap(
                { granted ->
                    if (!granted) {
                        Observable.empty<MutableList<Uri>>()
                    }
                    rxCamera.toObservable()
                }).subscribe(
                { uri ->
                    imageSelectionSubject.onNext(uri)
                    imageSelectionSubject.onComplete()
                },
                {
                    imageSelectionSubject.onError(it)
                    error { "Error during capturing photo by camera" }
                })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun clear() {
        galleryDisposable?.dispose()
        cameraDisposable?.dispose()
    }
}