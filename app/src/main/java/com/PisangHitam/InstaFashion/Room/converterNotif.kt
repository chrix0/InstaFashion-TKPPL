package com.PisangHitam.InstaFashion.Room

import androidx.room.TypeConverter
import com.PisangHitam.InstaFashion.classRecNotif
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.newFixedThreadPoolContext

class converterNotif {
    @TypeConverter
    open fun StringToList(string : String): MutableList<classRecNotif>{
        val gson = com.google.gson.Gson()
        if (string == null) {
            return java.util.Collections.emptyList();
        }

        var listType = object : com.google.gson.reflect.TypeToken<MutableList<classRecNotif>>() {}.type

        return gson.fromJson(string, listType)
    }

    @TypeConverter
    open fun ListToString(list: MutableList<classRecNotif>): String? {
        val gson = com.google.gson.Gson()
        return gson.toJson(list)
    }
}