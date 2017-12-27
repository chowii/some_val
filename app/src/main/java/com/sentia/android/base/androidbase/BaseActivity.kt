package com.sentia.android.base.androidbase

import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.LazyKodein

/**
 * Created by mariolopez on 27/12/17.
 */
abstract class BaseActivity : AppCompatActivity(){
    val kodein: LazyKodein = LazyKodein { App.context!!.kodein }
}