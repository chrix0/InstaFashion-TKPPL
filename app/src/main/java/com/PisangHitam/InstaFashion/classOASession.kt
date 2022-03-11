package com.PisangHitam.InstaFashion

import android.graphics.Bitmap
import dev.jorgecastillo.androidcolorx.library.HEXColor

class classOASession{
    lateinit var petPic : Bitmap
    lateinit var outiftPic : Bitmap
    var insertedPet = false
    lateinit var petHex : HEXColor
    lateinit var outfitHex : HEXColor
    var insertedOutfit = false
    var rec : MutableList<HEXColor> = mutableListOf()
    var similiar : MutableList<Float> = mutableListOf()
}

