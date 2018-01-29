package com.sentia.android.base.vis.search

import android.os.Bundle
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseActivity
import com.sentia.android.base.vis.login.LoginActivity
import com.sentia.android.base.vis.upload.Uploader
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor

/**
 * Created by mariolopez on 11/1/18.
 */
class SearchActivity : BaseActivity() {

    private val uploader by kodein.instance<Uploader>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if (!authManager.isUserLogged()) {
            startActivity(intentFor<LoginActivity>().clearTask())
            finish()
        }
        lifecycle.addObserver(uploader)
    }
}