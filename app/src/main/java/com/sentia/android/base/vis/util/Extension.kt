package com.sentia.android.base.vis.util

import android.content.Intent
import android.graphics.PorterDuff
import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.sentia.android.base.vis.R
import org.jetbrains.anko.internals.AnkoInternals

/**
 * Created by mariolopez on 8/1/18.
 */

//fun FragmentActivity.replaceFragment(fragment: Fragment,
//                                     @IdRes fragmentContainerId: Int = R.id.fragment_container): Int {
//    val tag = fragment::class.java.name
//    return supportFragmentManager.beginTransaction()
//            .replace(fragmentContainerId,
//                    supportFragmentManager
//                            .findFragmentByTag(tag)
//                            ?: fragment, tag)
//            .commit()
//}
inline fun <reified T: Any> Fragment.intentFor(vararg params: Pair<String, Any?>): Intent {
    return AnkoInternals.createIntent(this.context, T::class.java, params)
}

fun ActionBar?.tintBackArrow(@ColorRes color: Int) {
    val upArrow = ContextCompat.getDrawable(this?.themedContext, R.drawable.abc_ic_ab_back_material)
    upArrow.setColorFilter(ContextCompat.getColor(this?.themedContext, color), PorterDuff.Mode.SRC_ATOP)
    this?.setHomeAsUpIndicator(upArrow)

}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}
fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int){
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}


fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction{replace(frameId, fragment)}
}