package com.PisangHitam.InstaFashion

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.PisangHitam.InstaFashion.Room.*
import kotlinx.android.parcel.Parcelize

//Composite key
@Entity @Parcelize
data class classAccount(
    @PrimaryKey(autoGenerate = true)
    var id : Int,

    @ColumnInfo(name = "COLUMN_USERNAME")
    var userName : String = "",

    @ColumnInfo(name = "COLUMN_PASS") var password : String = "",

    @ColumnInfo(name = "COLUMN_FULLNAME") var fullName : String = "",

    @ColumnInfo(name = "COLUMN_EMAIL") var email : String = "",

    @ColumnInfo(name = "COLUMN_CART") @TypeConverters(converterCart::class)
    var cartContent : MutableList<classItemBasket> = mutableListOf(),

    @ColumnInfo(name = "COLUMN_ADDRESS") @TypeConverters(converterAddress::class)
    var shippingAddress : MutableList<String> = mutableListOf(),

    @ColumnInfo(name = "COLUMN_PHONE") var phoneNumber: String  = "",

    @ColumnInfo(name = "COLUMN_TRANSACTION") @TypeConverters(converterTrans::class)
    var transactionHistory : MutableList<classTransaction> = mutableListOf(),

    @ColumnInfo(name = "COLUMN_WISHLIST") @TypeConverters(converterProduk::class)
    var wishlist : MutableList<classProduk> = mutableListOf(),

    @ColumnInfo(name = "COLUMN_NOTIF") @TypeConverters(converterNotif::class)
    var notifications : MutableList<classRecNotif> = mutableListOf()
) : Parcelable