package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.PisangHitam.InstaFashion.classItemBasket
import kotlinx.android.synthetic.main.activity_shop_basket.*

class shop_basket : AppCompatActivity() {
    private lateinit var adapter : recycler_basket_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_basket)

        val actionbar = supportActionBar
        actionbar!!.title = "Basket"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val intentData = intent

        val db = singletonData.getRoomHelper(this)
        var userArray = db.daoAccount().getAccById(singletonData.currentAccId)

        var user : classAccount?
        var cartContent : MutableList<classItemBasket>?

        if(userArray.isEmpty()){
            user = db.daoAccount().getAccUserCheck("TEST")[0]
        }
        else{
            user = userArray[0]
        }

        val allData = user.cartContent
//            singletonData.accList[singletonData.currentAccId].cartContent

        if(intentData.hasExtra(ADD_TO_CART)){
            val item = intentData.getParcelableExtra<classItemBasket>(ADD_TO_CART) as classItemBasket
            var idProduk = item.idProduk
            var ditemukan = false
            var indexDitemukan = -1

            for(i in 0 until allData.size){
                if(allData[i].idProduk == idProduk){
                    ditemukan = true
                    indexDitemukan = i
                }
            }
            if (ditemukan){
                allData[indexDitemukan].quantity += item.quantity
            }
            else{
                allData.add(item)
            }

            //UPDATE
            val user = singletonData.getCurUserObj(this)
            user?.cartContent = allData
            db.daoAccount().updateAcc(user!!)
        }

        allData.reverse()
//        singletonData.getCurUserObj(this)!!.cartContent
        adapter = recycler_basket_adapter(this, allData){
            adapter.notifyDataSetChanged()
            this.subtotal.setText("Rp." + singletonData.formatHarga(singletonData.subtotalInCart(allData)))
        }
        basketList.layoutManager = LinearLayoutManager(this)
        basketList.adapter = adapter

        var ongkos = 5000

        subtotal.setText("Rp." +singletonData.formatHarga(singletonData.subtotalInCart(allData)))
        //ongkir.setText(singletonData.formatHarga(ongkos))
        //total.setText(singletonData.formatHarga(singletonData.totalInCart()))

        toCheckout.setOnClickListener {
            if(adapter.itemCount == 0){
                Toast.makeText(this, getString(R.string.empty_basket),Toast.LENGTH_LONG).show()
            }
            else{
                var checkout = Intent(this, shop_checkout::class.java)
                startActivity(checkout)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}