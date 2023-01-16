package com.PisangHitam.InstaFashion.Room

import androidx.room.TypeConverter
import com.PisangHitam.InstaFashion.classItemBasket
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.newFixedThreadPoolContext

import java.util.Collections
class converterCart {
//    Dengan GSON
//    https://medium.com/@toddcookevt/android-room-storing-lists-of-objects-766cca57e3f9

    @TypeConverter
    open fun StringToList(string : String): MutableList<classItemBasket>{
        val gson = Gson()
        if (string == null) {
            return Collections.emptyList();
        }

        var listType = object : TypeToken<MutableList<classItemBasket>>() {}.type

        return gson.fromJson(string, listType)
    }

    @TypeConverter
    open fun ListToString(list: MutableList<classItemBasket>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}