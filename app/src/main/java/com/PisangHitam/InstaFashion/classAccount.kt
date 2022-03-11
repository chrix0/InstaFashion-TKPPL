package com.PisangHitam.InstaFashion

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class classAccount(
    var id : Int,
    var userName: String,
    var password: String,
    var fullName: String,
    var email: String,
    var cartContent: MutableList<classItemBasket>,
    var shippingAddress : MutableList<String>,
    var phoneNumber : String = "",
    var transactionHistory: MutableList<classTransaction>
) : Parcelable