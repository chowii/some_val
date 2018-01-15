package com.sentia.android.base.vis.util

import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.data.room.entity.Vehicle
import java.util.*

/**
 * Created by mariolopez on 15/1/18.
 */
class TestDataProvider {
    companion object {
        fun getInspections(): List<Inspection> {
            return listOf(
                    Inspection(123, "NSW", false, false, false, false, true, false, false, false, "noIDEA", "randomValue", "someDate", "waxon_waxoff", "mint", "noidea2"
                            , createRandomVehicle()),
                    Inspection(12, "QSL", true, true, true, true, true, true, true, true, "noIDEA2", "randomValue2", "someDate2", "waxon_waxoff2", "filthy", "noidea2"
                            , createRandomVehicle()))

        }

        private fun createRandomVehicle(): Vehicle {
            val r = Random()
            return Vehicle(idVehicle = r.nextLong(), model = "Mondeo" + r.nextInt(), make = "Ford", rego = "12/21/2017", vin = "123123123")
        }

    }
}