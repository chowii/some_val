package com.sentia.android.base.vis.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseActivity
import com.sentia.android.base.vis.login.LoginActivity
import com.sentia.android.base.vis.lookups.InitialValuesManager
import com.sentia.android.base.vis.lookups.LoadedOffline
import com.sentia.android.base.vis.lookups.Login
import com.sentia.android.base.vis.lookups.ToLoad
import com.sentia.android.base.vis.search.SearchActivity
import com.sentia.android.base.vis.util.Resource.Status.*
import com.sentia.android.base.vis.util.connectivity.NetworkStatusManager
import io.reactivex.Single
import io.reactivex.functions.Function4
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toSingle
import org.jetbrains.anko.*

/**
 * Created by mariolopez on 31/1/18.
 */

class SplashActivity : BaseActivity() {

    override val handleExitScreenEvent: Boolean
        get() = false

    private lateinit var splashViewModel: SplashActivityViewModel

    private val networkManager: NetworkStatusManager by kodein.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashViewModel = ViewModelProviders.of(this).get(SplashActivityViewModel::class.java)
        lifecycle.addObserver(splashViewModel)

        compositeDisposable += Single.zip(authManager.isUserLogged().toSingle(),
                networkManager.isConnected().toSingle(),
                splashViewModel.getDepotsCount().toObservable().firstOrError(),
                splashViewModel.getLookupCount().toObservable().firstOrError(),
                Function4 { userLogged: Boolean, isConnected: Boolean, depotsCount: Int, lookupsCount: Int ->
                    InitialValuesManager.getInitialValueState(userLogged, isConnected, depotsCount, lookupsCount)
                })
                .subscribeBy(onSuccess = { initialValuesState ->
                    when (initialValuesState) {
                        ToLoad -> loadValues()
                        LoadedOffline -> launchSearchActivity()
                        Login -> {
                            startActivity(intentFor<LoginActivity>().clearTask().newTask())
                            finish()
                        }
                    }
                })
    }

    private fun loadValues() {
        splashViewModel.loadInitValues().observe(this, Observer {
            when (it?.status) {
                SUCCESS -> {
                    launchSearchActivity()
                }
                ERROR -> {
                    alert {
                        title = getString(R.string.alert_attention)
                        message = getString(R.string.alert_need_initial_values_needed)

                        yesButton { loadValues() }
                        noButton { finish() }

                    }.show()
                    error { it.exception }
                }
                LOADING -> {/*do nothing*/
                }
            }
        })
    }

    private fun launchSearchActivity() {
        startActivity(intentFor<SearchActivity>().newTask().clearTask())
        finish()
    }
}
