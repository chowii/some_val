package com.sentia.android.base.vis.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.App
import com.sentia.android.base.vis.api.auth.AuthManager
import com.sentia.android.base.vis.api.auth.AuthPass
import com.sentia.android.base.vis.api.auth.NotAuthorised
import com.sentia.android.base.vis.login.LoginActivity
import com.sentia.android.base.vis.util.RxBus
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor

/**
 * Created by mariolopez on 27/12/17.
 */
abstract class BaseActivity : AppCompatActivity(), AnkoLogger {
    val kodein: LazyKodein = LazyKodein { App.context!!.kodein }
    protected val authManager: AuthManager by kodein.instance()
    protected open val handleExitScreenEvent = true
    private val rxBus: RxBus by kodein.instance()
    protected val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxBus.observe(AuthPass::class.java).subscribe {
            when (it) {
                is NotAuthorised -> {
                    if (handleExitScreenEvent) {
                        finishAffinity()
                        startActivity(intentFor<LoginActivity>().clearTask())
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}