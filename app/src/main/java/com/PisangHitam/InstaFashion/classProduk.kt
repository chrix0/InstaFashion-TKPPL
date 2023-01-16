package com.PisangHitam.InstaFashion

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.picasso.Picasso
import kotlinx.android.parcel.Parcelize

@Parcelize @Entity
class classProduk(
    @PrimaryKey var idProduk: Int,
    @ColumnInfo(name = "COLUMN_NAMA_PRODUK") var namaProduk: String,
    @ColumnInfo(name = "COLUMN_HARGA_PRODUK")var hargaProduk: Int,
    @ColumnInfo(name = "COLUMN_URL_PRODUK")var urlGambarProduk: String,
    @ColumnInfo(name = "COLUMN_CAT_PRODUK") var category: String,
    @ColumnInfo(name = "COLUMN_DESC_PRODUK") var description: String
    ): Parcelable {

    @Ignore
    var savedBitmap : Bitmap? = null

    fun getBitmapBackground() : Bitmap {
        var getBitmap = Picasso.get().load(urlGambarProduk).get()
        Thread.sleep(700)
        getBitmap = singletonData.cropThis(getBitmap)
        return getBitmap
    }
}