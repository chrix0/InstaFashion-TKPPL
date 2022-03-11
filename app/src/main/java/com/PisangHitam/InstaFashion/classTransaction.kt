package com.PisangHitam.InstaFashion

import android.os.Parcelable
import com.PisangHitam.InstaFashion.classItemBasket
import kotlinx.android.parcel.Parcelize

@Parcelize
data class classTransaction(
    var id: Int = 0,
    var items: MutableList<classItemBasket> = mutableListOf(),
    var method: String = "",
    var address: MutableList<String> = mutableListOf(),
    var phoneNumber: String = "",
    var datePurchase : String = "",
    var subTotal : Int = 0,
    var shippingCost : Int = 0,
    var Total : Int = 0
) : Parcelable