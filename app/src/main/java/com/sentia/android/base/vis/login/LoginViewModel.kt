package com.sentia.android.base.vis.login

import android.arch.lifecycle.LiveData
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.api.model.LoginResult
import com.sentia.android.base.vis.base.BaseViewModel
import com.sentia.android.base.vis.data.InspectionRepository
import com.sentia.android.base.vis.util.Resource

/**
 * Created by mariolopez on 17/1/18.
 */
class LoginViewModel : BaseViewModel() {

    override val repository by kodein.instance<InspectionRepository>()

    fun login(username: String, password: String): LiveData<Resource<LoginResult>> =
            repository.login(username, password)

    fun loadInitValues() = repository.getInitValues()
}