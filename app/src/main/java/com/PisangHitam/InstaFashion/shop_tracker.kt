package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_shop_product_list.*
import kotlinx.android.synthetic.main.activity_shop_tracker.*
import org.jetbrains.anko.startActivity
import org.w3c.dom.Text

class shop_tracker : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_tracker)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.purchase_history_title)
        actionbar.setDisplayHomeAsUpEnabled(true)

        var user = singletonData.getCurUserObj(this)

        var adapter = recycler_shoptransaction_adapter(this, user!!.transactionHistory){
            var layout = layoutInflater.inflate(R.layout.dialog_shop_history_details,null)
            var dialog = AlertDialog.Builder(this).apply{
                setView(layout)
                setTitle(getString(R.string.purchase_details_title))
            }
            var creator = dialog.create()

            var id = layout.findViewById<TextView>(R.id.transactionID)
            var username = layout.findViewById<TextView>(R.id.username)
            var datetime = layout.findViewById<TextView>(R.id.datePurchase)
            var method = layout.findViewById<TextView>(R.id.method)
            var address = layout.findViewById<TextView>(R.id.address)
            var phone = layout.findViewById<TextView>(R.id.phone)
            var subtotal = layout.findViewById<TextView>(R.id.subtotal)
            var cost = layout.findViewById<TextView>(R.id.shipCost)
            var total = layout.findViewById<TextView>(R.id.total)
            var close = layout.findViewById<TextView>(R.id.closeDetails)

            id.setText(it.id.toString())
            username.setText(user!!.userName)
            datetime.setText(it.datePurchase)
            method.setText(it.method)
            address.setText(singletonData.formatAlamat(it.address))
            phone.setText(it.phoneNumber)
            subtotal.setText("Rp." + singletonData.formatHarga(it.subTotal))
            cost.setText("Rp." + singletonData.formatHarga(it.shippingCost))
            total.setText("Rp." + singletonData.formatHarga(it.Total))

            close.setOnClickListener {
                creator.cancel()
            }
            creator.show()
        }
        listHistory.layoutManager = LinearLayoutManager(this)
        listHistory.adapter = adapter

        returnShop.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}