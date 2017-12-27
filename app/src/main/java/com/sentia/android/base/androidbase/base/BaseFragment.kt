package com.sentia.android.base.androidbase.base

import android.app.Fragment
import com.github.salomonbrys.kodein.LazyKodein
import com.sentia.android.base.androidbase.App

/**
 * Created by mariolopez on 27/12/17.
 */
abstract class BaseFragment: Fragment(){
    val kodein: LazyKodein = LazyKodein { App.context!!.kodein }
}