package com.sentia.android.base.vis.data.room

/**
 * Created by mariolopez on 28/12/17.
 */
class RoomContract {

    companion object {

        const val DATABASE_VIS = "Vis_Inspection.db"

        const val TABLE_INSPECTIONS = "Inspections_Table"
        const val TABLE_VEHICLES = "Vehicle_Model_Table"
        const val TABLE_VEHICLES_TYRES = "Tyres_Model_Table"
        const val TABLE_VEHICLES_ACCESSORIES = "Accessories_model_Table"
        const val TABLE_INSPECTION_DEPOT = "Depots_Model_Table"
        const val TABLE_VEHICLES_DAMAGE_REPORT = "Damage_report_Table"
        const val TABLE_VEHICLES_DAMAGE_ITEM = "Damage_items_Table"
        const val TABLE_VEHICLES_LOCATION_JSON = "Location_Model_Table"
        const val TABLE_PHOTOS = "Photos_Model_Table"

        private const val SELECT_COUNT = "SELECT COUNT(*) FROM "
        internal const val SELECT_FROM = "SELECT * FROM "

        const val SELECT_INSPECTIONS_COUNT = SELECT_COUNT + TABLE_INSPECTIONS
        const val SELECT_INSPECTIONS = SELECT_FROM + TABLE_INSPECTIONS
    }
}