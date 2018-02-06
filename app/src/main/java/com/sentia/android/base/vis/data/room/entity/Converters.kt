package com.sentia.android.base.vis.data.room.entity

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by mariolopez on 23/1/18.
 */
class UploadStatusTypeConverter {

    @TypeConverter
    fun uploadStatusToString(uploadStatus: UploadStatus.Status): String {
        return uploadStatus.toString()
    }

    @TypeConverter
    fun uploadStatusStringToEnum(uploadStatusString: String): UploadStatus.Status {
        return UploadStatus.Status.valueOf(uploadStatusString)
    }

}

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

class LookupsTypeConverter {

    @TypeConverter
    fun lookupsListToString(lookups: List<LookupItem>): String {
        return Gson().toJson(lookups)
    }

    @TypeConverter
    fun lookupsJsonStringToList(jsonString: String): List<LookupItem> {
        val listType = object : TypeToken<List<LookupItem>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

}