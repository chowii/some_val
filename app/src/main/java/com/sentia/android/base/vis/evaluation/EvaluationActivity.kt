package com.sentia.android.base.vis.evaluation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseActivity
import com.sentia.android.base.vis.evaluation.inspection.EvaluationViewModel
import com.sentia.android.base.vis.search.SearchActivity
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID
import com.sentia.android.base.vis.util.Resource
import com.sentia.android.base.vis.util.tintBackArrow
import kotlinx.android.synthetic.main.activity_evaluation.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity


class EvaluationActivity : BaseActivity(), LifecycleOwner {

    private lateinit var inspectionViewModel: EvaluationViewModel

    fun initViewModel() {
        inspectionViewModel = ViewModelProviders.of(this).get(EvaluationViewModel::class.java)
        inspectionViewModel.let { lifecycle.addObserver(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluation)

        initViewModel()
        initUi()
    }

    override fun onStart() {
        super.onStart()
        inspectionViewModel.findInspection(intent.getLongExtra(KEY_INSPECTION_ID, 0L))
    }

    private fun initUi() {
        setSupportActionBar(toolbar_evaluation)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.tintBackArrow(R.color.white)

        tl_evaluation.setupWithViewPager(vp_tabs_evaluation)
        vp_tabs_evaluation.adapter = EvaluationTabsPagerAdapter(supportFragmentManager, resources,
                intent?.getLongExtra(KEY_INSPECTION_ID, 0L) ?: 0L)

        tv_evaluation_finish.setOnClickListener {
            inspectionViewModel.persistChanges().observe(this, Observer {
                when (it?.status) {
                    Resource.Status.SUCCESS -> {
                        pb_evaluation.visibility = View.INVISIBLE
                        info { "PERSISTED Mutation" }
                        startActivity<SearchActivity>()
                        finishAffinity()
                    }
                    Resource.Status.LOADING -> {
                        pb_evaluation.visibility = View.VISIBLE
                    }
                    else -> throw it?.exception ?: Exception("this shouldn't happen ")
                }
            })
        }
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