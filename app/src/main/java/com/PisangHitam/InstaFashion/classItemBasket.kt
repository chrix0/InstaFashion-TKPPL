package com.PisangHitam.InstaFashion

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class classItemBasket(
    var idProduk: Int,
    var namaProduk: String,
    var hargaProduk: Int,
    var urlGambarProduk: String,
    var description: String,
    var quantity: Int = 1
) : Parcelable