package com.sentia.android.base.vis.base

import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.LazyKodein
import com.sentia.android.base.vis.App

/**
 * Created by mariolopez on 27/12/17.
 */
abstract class BaseActivity : AppCompatActivity(){
    val kodein: LazyKodein = LazyKodein { App.context!!.kodein }
}