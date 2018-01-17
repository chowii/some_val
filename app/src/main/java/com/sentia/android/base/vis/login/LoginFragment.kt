package com.sentia.android.base.vis.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseFragment
import com.sentia.android.base.vis.search.SearchActivity
import com.sentia.android.base.vis.util.*
import com.sentia.android.base.vis.util.Resource.Status.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.design.snackbar

/**
 * Created by mariolopez on 16/1/18.
 */
class LoginFragment : BaseFragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun initViewModel() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel.let { lifecycle.addObserver(it) }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()

    }

    private fun initUi() {

        bt_login.setOnClickListener {

            val email = et_user_email.text.toString()
            val password = et_user_password.text.toString()

            if (validate(email, password)) {

                loginViewModel.login(email, password)
                        .observe(this, Observer {
                            when (it!!.status) {

                                SUCCESS -> {
                                    authManager.login(it.data)
                                    pb_loading.invisble()
                                    snackbar(this.view!!.rootView, "EUREKA")
                                    startActivity(intentFor<SearchActivity>().clearTask())
                                }
                                ERROR -> {
                                    pb_loading.invisble()
                                    snackbar(this.view!!.rootView, "errorLogin")
                                }
                                LOADING -> pb_loading.visible()
                            }
                        })
            }
        }
    }

    private fun validate(email: String, password: String): Boolean {

        if (!email.isEmail()) {
            til_email.error = getString(R.string.til_error_email)
        }

        val isMinLengthPassword = password.length >= PASSWORD_MIN_LENGTH
        if (!isMinLengthPassword) {
            til_password.error = getString(R.string.til_error_password)
        }

        return email.isEmail() && isMinLengthPassword
    }
}