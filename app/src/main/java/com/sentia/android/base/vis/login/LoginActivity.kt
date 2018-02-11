package com.sentia.android.base.vis.login

import android.os.Bundle
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseActivity

/**
 * Created by mariolopez on 16/1/18.
 */
class LoginActivity : BaseActivity() {

    override val handleExitScreenEvent: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}