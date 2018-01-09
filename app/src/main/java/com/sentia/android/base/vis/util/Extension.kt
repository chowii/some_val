package com.sentia.android.base.vis.util

/**
 * Created by mariolopez on 8/1/18.
 */
//this requires a companion object
val Any.tag: String?
    get() {
        return this::javaClass.name
    }