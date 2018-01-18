package com.sentia.android.base.vis.evaluation

import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseActivity
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID
import com.sentia.android.base.vis.util.tintBackArrow
import kotlinx.android.synthetic.main.activity_evaluation.*


class EvaluationActivity : BaseActivity(), LifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluation)

        setSupportActionBar(toolbar_evaluation)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.tintBackArrow(R.color.white)


        tl_evaluation.setupWithViewPager(vp_tabs_evaluation)
        vp_tabs_evaluation.adapter = EvaluationTabsPagerAdapter(supportFragmentManager, resources,intent.getLongExtra(KEY_INSPECTION_ID,0L))

    }

}