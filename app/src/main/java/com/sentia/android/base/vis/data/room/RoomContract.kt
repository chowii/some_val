package com.sentia.android.base.vis.data.room

/**
 * Created by mariolopez on 28/12/17.
 */
class RoomContract {

    companion object {

        const val DATABASE_VIS = "Vis_Inspection.db"

        const val TABLE_VEHICLES = "Vehicle_Model_Table"
        const val TABLE_VEHICLES_TYRES = "Tyres_Model_Table"
        const val TABLE_VEHICLES_SERVICING = "Servicing_Model_Table"
        const val TABLE_VEHICLES_ADD_ONS = "Add_ons_Model_Table"
        const val TABLE_VEHICLES_OTHERS = "Others_Model_Table"
        const val TABLE_VEHICLES_PHOTOS = "Photos_Model_Table"

        private const val SELECT_COUNT = "SELECT COUNT(*) FROM "
        internal const val SELECT_FROM = "SELECT * FROM "

        const val SELECT_VEHICLES_COUNT = SELECT_COUNT + TABLE_VEHICLES
        const val SELECT_VEHICLES = SELECT_FROM + TABLE_VEHICLES
    }
}