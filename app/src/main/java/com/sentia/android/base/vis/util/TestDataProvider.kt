package com.sentia.android.base.vis.util

import com.sentia.android.base.vis.data.room.entity.Image
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
                            , createRandomVehicle()).apply { images.addAll(createMockPhotos()) },
                    Inspection(12, "QSL", true, true, true, true, true, true, true, true, "noIDEA2", "randomValue2", "someDate2", "waxon_waxoff2", "filthy", "noidea2"
                            , createRandomVehicle()).apply { images.addAll(createMockPhotos()) })

        }

        private fun createRandomVehicle(): Vehicle {
            val r = Random()
            return Vehicle(idVehicle = r.nextLong(), model = "Mondeo" + r.nextInt(), make = "Ford", rego = "12/21/2017", vin = "123123123")
        }

        private fun createMockPhotos() = mutableListOf<Image>().apply {
            add(Image(1, "Name1", false,false,"base64", "Overlay1"))
            add(Image(2, "Name2",false,false, "base64", "Overlay2"))
            add(Image(3, "Name3",false,false,"base64", "Overlay3"))
            add(Image(4, "Name4",false,false,"base64", "Overlay4"))
            add(Image(5, "Name5",false,false,"base64", "Overlay5"))
            add(Image(6, "Name6",false,false,"base64", "Overlay6"))
            add(Image(7, "Name7",false,false,"base64", "Overlay7"))
        }
    }
}