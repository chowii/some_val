package com.sentia.android.base.vis.data.room

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sentia.android.base.vis.data.room.entity.Accessory

/**
 * Created by Jovan on 23/1/18.
 */

class AccessoryTypeConverter {

    @TypeConverter
    fun accessoryListToString(accessories: List<Accessory>): String {
        return Gson().toJson(accessories)
    }

    @TypeConverter
    fun accessoryJsonStringToList(jsonString: String): List<Accessory> {
        val listType = object : TypeToken<List<Accessory>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

}