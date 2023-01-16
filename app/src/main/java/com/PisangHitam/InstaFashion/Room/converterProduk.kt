package com.PisangHitam.InstaFashion.Room

import androidx.room.TypeConverter
import com.PisangHitam.InstaFashion.classProduk
import com.PisangHitam.InstaFashion.classTransaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class converterProduk {
    @TypeConverter
    open fun StringToList(string : String): MutableList<classProduk>{
        val gson = Gson()
        if (string == null) {
            return Collections.emptyList();
        }

        var listType = object : TypeToken<MutableList<classProduk>>() {}.type

        return gson.fromJson(string, listType)
    }
    @TypeConverter
    open fun ListToString(list: MutableList<classProduk>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}