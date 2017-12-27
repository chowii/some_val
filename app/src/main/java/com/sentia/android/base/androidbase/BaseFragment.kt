package com.sentia.android.base.androidbase

import android.app.Fragment
import com.github.salomonbrys.kodein.LazyKodein

/**
 * Created by mariolopez on 27/12/17.
 */
abstract class BaseFragment: Fragment(){
    val kodein: LazyKodein = LazyKodein { App.context!!.kodein }
}