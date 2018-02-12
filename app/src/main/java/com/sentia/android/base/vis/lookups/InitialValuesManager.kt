package com.sentia.android.base.vis.lookups

/**
 * Created by mariolopez on 12/2/18.
 */
class InitialValuesManager {
    companion object {
        fun getInitialValueState(userLogged: Boolean, connected: Boolean, depotsCount: Int, lookupsCount: Int): InitialValuesState =
                if (userLogged && !connected && depotsCount > 0 && lookupsCount > 0) {
                    LoadedOffline
                } else if (userLogged && connected || (depotsCount == 0 || lookupsCount == 0)) {
                    ToLoad
                } else {
                    Login
                }
    }
}

sealed class InitialValuesState
object LoadedOffline : InitialValuesState()
object ToLoad : InitialValuesState()
object Login : InitialValuesState()