package com.PisangHitam.InstaFashion

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.graphics.Bitmap
import android.os.AsyncTask
import android.view.View
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.PisangHitam.InstaFashion.OA_petPic
import com.PisangHitam.InstaFashion.classProduk
import dev.jorgecastillo.androidcolorx.library.RGBColor
import dev.jorgecastillo.androidcolorx.library.asRgb
import kotlinx.android.synthetic.main.activity_oa_recommend.*

@Suppress("DEPRECATION")
class oa_recommend : AppCompatActivity() {
    var firstnAnalog = mutableListOf<classProduk>()
    var firstnComplement = mutableListOf<classProduk>()

    lateinit var context : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        Async().execute()
        setContentView(R.layout.activity_oa_recommend)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.recommedationTitle)

        tryAgain.setOnClickListener {
            var intent = Intent(this, OA_petPic::class.java)
            startActivity(intent)
        }

        backToShop.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra(RETURN_LAST_TAB, "SHOP")
            startActivity(intent)
        }
    }

    inner class Async : AsyncTask<Void,Void,Unit>(){
        var dialog = ProgressDialog(context)
        override fun doInBackground(vararg p0: Void?): Unit? {
            if (!singletonData.allImageProcessed){
                for(i in 0 until singletonData.petOutfitList.size){
                    singletonData.petOutfitList[i].savedBitmap = singletonData.petOutfitList[i].getBitmapBackground()
                }
                singletonData.allImageProcessed = true
            }
            firstnComplement= checkColor(singletonData.OASession.rec[0].asRgb())
            firstnAnalog = mutableListOf()
            firstnAnalog.addAll(checkColor(singletonData.OASession.rec[1].asRgb()))
            firstnAnalog.removeAll(checkColor(singletonData.OASession.rec[2].asRgb()))
            firstnAnalog.addAll(checkColor(singletonData.OASession.rec[2].asRgb()))

            return null
        }
        override fun onPreExecute() {
            super.onPreExecute()
            dialog.setMessage(getString(R.string.recommendLoading))
            dialog.setCancelable(false)
            dialog.show()
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            if (dialog.isShowing()) {
                dialog.dismiss()
            }

            var analog = recycler_products_adapter(firstnAnalog){
                val info = Intent(context, shop_infoProduk::class.java)
                info.putExtra(SHOW_PRODUCT_INFO, it)
                info.putExtra(CHANGE_TITLE,context.getString(R.string.product_info_title))
                startActivity(info)
            }
            itemAnalog.adapter = analog
            itemAnalog.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)

            var comp = recycler_products_adapter(firstnComplement){
                val info = Intent(context, shop_infoProduk::class.java)
                info.putExtra(SHOW_PRODUCT_INFO, it)
                info.putExtra(CHANGE_TITLE,context.getString(R.string.product_info_title))
                startActivity(info)
            }
            itemComp.adapter = comp
            itemComp.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)

            if (analog.itemCount == 0){
                zeroAnalog.setVisibility(View.VISIBLE)
            }

            if(comp.itemCount == 0){
                zeroComp.setVisibility(View.VISIBLE)
            }
        }
    }

    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).maximumColorCount(16).generate()

    fun checkColor(color : RGBColor) : MutableList<classProduk> {
        var temp : MutableList<classProduk> = mutableListOf()

        for(i : classProduk in singletonData.petOutfitList){
            var colorPalette = createPaletteSync(i.savedBitmap!!)
            var dominantItemColor = colorPalette.dominantSwatch!!.rgb.asRgb()

            if (judge(color, dominantItemColor)){
                temp.add(i)
            }
        }
        return temp
    }

    fun judge(rgb1 : RGBColor, rgb2 : RGBColor) : Boolean{
        //Metode yang digunakan untuk menganalisis hanya untuk mengukur kemiripan dari nilai RGB saja,
        //jadi ada kemungkinan produk dengan warna yang berbeda akan muncul di rekomendasi.
        var showColor1 = colorFilter(rgb1)
        var showColor2 = colorFilter(rgb2)

        //Untuk mengatasi hal tersebut, saya mencari channel warna dengan nilai maksimum untuk kedua
        //gambar untuk menentukan jenis warna. Jika keduanya sejenis, proses analisis bisa dilakukan.
        if(!showColor1.equals(showColor2)){
            var r = Math.abs(rgb1.red - rgb2.red)
            var g = Math.abs(rgb1.green - rgb2.green)
            var b = Math.abs(rgb1.blue - rgb2.blue)

            var pR : Float= (r / 255f)
            var pG : Float= (g / 255f)
            var pB : Float= (b / 255f)

            var similiarity : Float = 100f - ((pR + pG + pB) / 3 * 100)

            if(similiarity >= 80){
                return true
            }
            return false
        }
        //Selain itu tidak akan diproses
        return false
    }

    fun colorFilter(rgb : RGBColor) : String{
        var maxIndex : Int = 0
        var split = mutableListOf<Int>(rgb.red, rgb.green, rgb.blue)
        for((idx, elem) in split.withIndex()){
            if(split[maxIndex] <= elem){
                maxIndex = idx
            }
        }
        //Kelemahannya untuk warna hitam atau putih mungkin kurang cocok
        var conclusion = when(maxIndex){
            0 -> "RED"
            1 -> "GREEN"
            2 -> "BLUE"
            else -> "ANY"
        }
        return conclusion
    }
}