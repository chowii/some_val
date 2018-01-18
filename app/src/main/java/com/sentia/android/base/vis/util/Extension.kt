package com.sentia.android.base.vis.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.sentia.android.base.vis.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
//Porting anko function to fragment
inline fun <reified T : Any> Fragment.intentFor(vararg params: Pair<String, Any?>): Intent {
    return AnkoInternals.createIntent(this.context, T::class.java, params)
}

inline fun <reified T : Activity> Fragment.startActivity(vararg params: Pair<String, Any>) {
    AnkoInternals.internalStartActivity(this.activity, T::class.java, params)
}

fun ActionBar?.tintBackArrow(@ColorRes color: Int) {
    val upArrow = ContextCompat.getDrawable(this?.themedContext, R.drawable.abc_ic_ab_back_material)
    upArrow.setColorFilter(ContextCompat.getColor(this?.themedContext, color), PorterDuff.Mode.SRC_ATOP)
    this?.setHomeAsUpIndicator(upArrow)

}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun <T> Observable<T>.forUi(): Observable<T> =
        this.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())



fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisble() {
    this.visibility = View.INVISIBLE
}

fun Activity.hideKeyboard() {
    currentFocus?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.hideKeyboard() {
    this.activity?.currentFocus?.let {
        val imm = this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Boolean?.orFalse(): Boolean = this ?: false

/**
 * @return return if the string is an email format
 */
fun String?.isEmail(): Boolean {
    if (this == null) {
        return false
    }
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * @return return if the string is an email format
 */
fun CharSequence?.isEmail(): Boolean = this.toString().isEmail()


fun Fragment.toast(message: CharSequence) = Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()