package com.PisangHitam.InstaFashion

import android.graphics.Bitmap
import android.os.Parcelable
import com.rain.adopets.singletonData
import com.squareup.picasso.Picasso
import kotlinx.android.parcel.Parcelize

@Parcelize
class classProduk(var idProduk: Int,
                  var namaProduk: String,
                  var hargaProduk: Int,
                  var urlGambarProduk: String,
                  var description: String ): Parcelable {

    var savedBitmap : Bitmap? = null

    fun getBitmapBackground() : Bitmap{
        var getBitmap = Picasso.get().load(urlGambarProduk).get()
        Thread.sleep(700)
        getBitmap = singletonData.cropThis(getBitmap)
        return getBitmap
    }
}