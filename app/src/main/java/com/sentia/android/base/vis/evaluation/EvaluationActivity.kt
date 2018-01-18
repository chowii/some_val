package com.sentia.android.base.vis.evaluation

import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import android.view.MenuItem
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseActivity
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID
import com.sentia.android.base.vis.util.tintBackArrow
import kotlinx.android.synthetic.main.activity_evaluation.*
import org.jetbrains.anko.alert


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
        vp_tabs_evaluation.adapter = EvaluationTabsPagerAdapter(supportFragmentManager, resources, intent.getLongExtra(KEY_INSPECTION_ID, 0L))

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                showClosingChangesDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showClosingChangesDialog() {
        alert {
            title = getString(R.string.alert_attention)
            negativeButton(android.R.string.cancel, { it.dismiss() })
            positiveButton(android.R.string.ok, {
                it.dismiss()
                super.onBackPressed()
            })
            message = getString(R.string.alert_warning_closing)

        }.show()
    }

    override fun onBackPressed() {
        showClosingChangesDialog()
    }
}