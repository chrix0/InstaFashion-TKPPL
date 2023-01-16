package com.PisangHitam.InstaFashion

import android.graphics.Bitmap
import android.os.Parcelable
import dev.jorgecastillo.androidcolorx.library.HEXColor
import kotlinx.android.parcel.Parcelize

@Parcelize
class classOASession : Parcelable{
    lateinit var pic1 : Bitmap
    lateinit var pic2 : Bitmap
    var insertedPic1 = false
    lateinit var pic1Hex : HEXColor
    lateinit var pic2Hex : HEXColor
    var insertedPic2 = false
    var rec : MutableList<HEXColor> = mutableListOf()
    var similiar : MutableList<Float> = mutableListOf()
}

