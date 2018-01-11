package com.sentia.android.base.vis.search

import android.os.Bundle
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseActivity

/**
 * Created by mariolopez on 11/1/18.
 */
class SearchActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}