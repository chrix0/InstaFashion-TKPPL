package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.PisangHitam.InstaFashion.classItemBasket
import com.PisangHitam.InstaFashion.classProduk
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_shop_info_produk.*

class shop_infoProduk : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_info_produk)

        val intentData = intent

        //Membuat action bar secara manual dengan tombol up
        val actionbar = supportActionBar
        actionbar!!.title = intentData.getStringExtra(CHANGE_TITLE)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val produk : classProduk = intentData.getParcelableExtra<classProduk>(SHOW_PRODUCT_INFO) as classProduk

        Picasso.get().load(produk.urlGambarProduk).into(productImg)
        name.text = produk.namaProduk
        price.text = "Rp." + singletonData.formatHarga(produk.hargaProduk)
        infoText.text = produk.description
        Quantity.text = "1"

        addToCart.setOnClickListener {
            val info = Intent(this, shop_basket::class.java)
            var itemBasket = classItemBasket(
                produk.idProduk,
                produk.namaProduk,
                produk.hargaProduk,
                produk.urlGambarProduk,
                produk.description,
                Quantity.text.toString().toInt()
            )
            info.putExtra(ADD_TO_CART, itemBasket)
            startActivity(info)
        }

        buttonInc.setOnClickListener{
            val before = Quantity.text.toString().toInt()
            var after = before + 1
            Quantity.setText(after.toString())
        }

        buttonDec.setOnClickListener{
            val before = Quantity.text.toString().toInt()
            if(before > 1) {
                var after = before - 1
                Quantity.setText(after.toString())
            }
        }
    }
    //Back ketika menekan tombol up
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}