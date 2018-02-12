package com.sentia.android.base.vis.splash

import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.base.BaseViewModel
import com.sentia.android.base.vis.data.InspectionRepository

/**
 * Created by mariolopez on 31/1/18.
 */
class SplashActivityViewModel : BaseViewModel() {
    override val repository by kodein.instance<InspectionRepository>()

    fun loadInitValues() = repository.getInitValues()
    fun getLookupCount() = repository.getTotalLookups()
    fun getDepotsCount() = repository.getTotalDepots()
}