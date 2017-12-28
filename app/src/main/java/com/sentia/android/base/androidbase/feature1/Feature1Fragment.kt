package com.sentia.android.base.androidbase.feature1

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentia.android.base.androidbase.R
import com.sentia.android.base.androidbase.base.BaseFragment
import com.sentia.android.base.androidbase.util.Resource.Resource.*

/**
 * Created by mariolopez on 27/12/17.
 */
class Feature1Fragment : BaseFragment() {
    private var feature1ViewModel: Feature1ViewModel? =null

    override fun initViewModel() {
       feature1ViewModel = ViewModelProviders.of(this).get(Feature1ViewModel::class.java)
       feature1ViewModel?.let { lifecycle.addObserver(it) }
       feature1ViewModel?.initLocalSamples()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_feature1, container, false)
        initUi()

        return view
    }

    private fun initUi() {
        //todo init recycler view
        feature1ViewModel?.loadSampleList()?.observe(this, Observer {
            val size = it?.data?.size
            when (it?.status){
                Status.SUCCESS -> TODO()
                Status.ERROR -> TODO()
                Status.LOADING -> TODO()
            }
            //todo-init recycler view
        })
    }

}