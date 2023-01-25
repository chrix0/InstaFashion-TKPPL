package com.PisangHitam.InstaFashion

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.jorgecastillo.androidcolorx.library.*
import kotlinx.android.synthetic.main.activity_oa_result.*
import kotlinx.android.synthetic.main.activity_shop_checkout.*

class OA_result : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oa_result)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.Analysis_Result)

        imagePet.setImageBitmap(singletonData.OASession.pic1)
        imageOutfit.setImageBitmap(singletonData.OASession.pic2)

        var petHex = Color.parseColor("${singletonData.OASession.pic1Hex}")
        var outfitHex = Color.parseColor("${singletonData.OASession.pic2Hex}")

        val compliment = petHex.complimentary()
        val analogous = petHex.analogous()
        singletonData.OASession.rec.addAll(mutableListOf(compliment.asHex(),
            analogous.first.asHex(), analogous.second.asHex()))

        var rec = singletonData.OASession.rec
        var rgb1 = outfitHex.asRgb()


        var Over85 = false
        for (i : HEXColor in rec){
            var rgb2 = i.asRgb()
            var r = Math.abs(rgb1.red - rgb2.red)
            var g = Math.abs(rgb1.green - rgb2.green)
            var b = Math.abs(rgb1.blue - rgb2.blue)

            var pR : Float= (r / 255f)
            var pG : Float= (g / 255f)
            var pB : Float= (b / 255f)

            var similiarity : Float = 100f - ((pR + pG + pB) / 3 * 100)

            if(similiarity >= 85){
                Over85 = true
            }
            singletonData.OASession.similiar.add("%.2f".format(similiarity).toFloat())
        }

        var sim =  singletonData.OASession.similiar

        var c1 = Color.parseColor("${rec[0]}")
        var c2 = Color.parseColor("${rec[1]}")
        var c3 = Color.parseColor("${rec[2]}")
        color1.setBackgroundColor(c1)
        color2.setBackgroundColor(c2)
        color3.setBackgroundColor(c3)
        color1.text = "${sim[0]}%"
        color2.text = "${sim[1]}%"
        color3.text = "${sim[2]}%"

        color1.setTextColor(if(c1.isDark()){
            Color.WHITE
        }else{
            Color.BLACK
        })

        color2.setTextColor(if(c2.isDark()){
            Color.WHITE
        }else{
            Color.BLACK
        })

        color3.setTextColor(if(c3.isDark()){
            Color.WHITE
        }else{
            Color.BLACK
        })

        when(Over85){
            true ->{
                resultTitle.text = "PASS"
                resultTitle.setTextColor(Color.GREEN)
                resultExplain.text = getString(R.string.result_pass)
            }
            else ->{
                resultTitle.text = "FAULTY"
                resultTitle.setTextColor(Color.RED)
                resultExplain.text = getString(R.string.result_faulty)
            }
        }

        toRekomendasi.setOnClickListener {
            var intent = Intent(this, oa_recommend::class.java)
            startActivity(intent)
        }
    }
}