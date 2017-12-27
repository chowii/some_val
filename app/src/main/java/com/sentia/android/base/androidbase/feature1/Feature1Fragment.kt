package com.sentia.android.base.androidbase.feature1

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.androidbase.R
import com.sentia.android.base.androidbase.api.RestAdapter
import com.sentia.android.base.androidbase.base.BaseFragment
import com.sentia.android.base.androidbase.util.RxBus

/**
 * Created by mariolopez on 27/12/17.
 */
class Feature1Fragment : BaseFragment() {
    val rxBus by kodein.instance<RxBus>()
    val RestAdapter by kodein.instance<RestAdapter>()

    private var feature1ViewModel: Feature1ViewModel? =null

    override fun initViewModel() {
       feature1ViewModel = ViewModelProviders.of(this).get(Feature1ViewModel::class.java)
       feature1ViewModel?.let { lifecycle.addObserver(it) }
       feature1ViewModel?.initLocalCurrencies()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_feature1,container,false)
    }

}