package com.PisangHitam.InstaFashion.Room

import androidx.room.TypeConverter
import com.PisangHitam.InstaFashion.classItemBasket
import com.PisangHitam.InstaFashion.classTransaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class converterTrans {
    @TypeConverter
    open fun StringToList(string : String): MutableList<classTransaction>{
        val gson = Gson()
        if (string == null) {
            return Collections.emptyList();
        }

        var listType = object : TypeToken<MutableList<classTransaction>>() {}.type

        return gson.fromJson(string, listType)
    }
    @TypeConverter
    open fun ListToString(list: MutableList<classTransaction>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}