package com.PisangHitam.InstaFashion.Room

import androidx.room.TypeConverter
import com.PisangHitam.InstaFashion.classItemBasket
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class converterAddress {
    @TypeConverter
    open fun StringToList(string : String): MutableList<String>{
        val gson = Gson()
        if (string == null) {
            return Collections.emptyList();
        }

        var listType = object : TypeToken<MutableList<String>>() {}.type

        return gson.fromJson(string, listType)
    }

    @TypeConverter
    open fun ListToString(list: MutableList<String>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}