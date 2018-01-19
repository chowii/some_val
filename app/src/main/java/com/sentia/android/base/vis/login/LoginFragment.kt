package com.sentia.android.base.vis.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseFragment
import com.sentia.android.base.vis.search.SearchActivity
import com.sentia.android.base.vis.util.*
import com.sentia.android.base.vis.util.Resource.Status.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.newTask


/**
 * Created by mariolopez on 16/1/18.
 */
class LoginFragment : BaseFragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun initViewModel() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel.let { lifecycle.addObserver(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        hideValidationError(et_user_email, til_email)
        hideValidationError(et_user_password, til_password)

        bt_login.setOnClickListener { validateAndLogin() }

        et_user_password.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                validateAndLogin()
                handled = true
            }
            handled
        }
    }

    private fun hideValidationError(editText: TextInputEditText, inputLayout: TextInputLayout) {
        editText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputLayout.error = null
            }
        }
    }

    private fun validateAndLogin() {
        val email = et_user_email.text.toString()
        val password = et_user_password.text.toString()

        if (validate(email, password)) {
            loginViewModel.login(email, password)
                    .observe(this, Observer {
                        when (it!!.status) {
                            SUCCESS -> {
                                authManager.login(it.data)
                                startActivity(intentFor<SearchActivity>().clearTask().newTask())
                            }
                            ERROR -> {
                                pb_loading.gone()
                                bt_login.visible()
                                snackbar(this.view!!.rootView, getString(R.string.login_error))
                            }
                            LOADING -> {
                                bt_login.gone()
                                pb_loading.visible()
                            }
                        }
                    })
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