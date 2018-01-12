package com.sentia.android.base.vis.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.github.salomonbrys.kodein.LazyKodein
import com.sentia.android.base.vis.App
import org.jetbrains.anko.AnkoLogger

/**
 * Created by mariolopez on 27/12/17.
 */
abstract class BaseFragment : Fragment(), AnkoLogger {

    val kodein: LazyKodein = LazyKodein { App.context!!.kodein }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    abstract fun initViewModel()
}