package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.PisangHitam.InstaFashion.classTransaction
import kotlinx.android.synthetic.main.activity_shop_checkout.*
import kotlinx.android.synthetic.main.activity_shop_checkout.subtotal
import kotlinx.android.synthetic.main.activity_shop_info_produk.*
import java.text.DecimalFormat
import java.util.*

class shop_checkout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_checkout)

        val actionbar = supportActionBar
        actionbar!!.title = "Checkout"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val user = singletonData.accList[singletonData.currentAccId]
        var curDate = Calendar.getInstance()
        var year = curDate.get(Calendar.YEAR)
        var month = curDate.get(Calendar.MONTH)
        var day = curDate.get(Calendar.DAY_OF_MONTH)
        //HOUR = 1 - 12, HOUR_OF_DAY = 24
        var hour = curDate.get(Calendar.HOUR_OF_DAY)
        var minute = curDate.get(Calendar.MINUTE)

        var transaksi = classTransaction(datePurchase = "${day}-${month}-${year} ${DecimalFormat("00").format(hour)}:${DecimalFormat("00").format(minute)}")

        transaksi.id = singletonData.accList[singletonData.currentAccId].transactionHistory.size
        //items
        transaksi.items.addAll(user.cartContent)

        val promptAddress : String = getString(R.string.promptAddress)
        val promptPhone : String = getString(R.string.promptPhone)

        //address cara 1
        var address = user.shippingAddress
        if(address[0].equals("") && address[1].equals("") && address[2].equals("") && address[3].equals("")){
            alamat.setText(promptAddress)
        }
        else{
            transaksi.address = user.shippingAddress
            alamat.setText(formatAlamat(user.shippingAddress))
        }
        //phoneNumber cara 1
        if(user.phoneNumber.equals("")){
            telp.setText(promptPhone)
        }
        else{
            transaksi.phoneNumber = user.phoneNumber
            telp.setText(user.phoneNumber)
        }

        //method
        kartu.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                transaksi.method = kartu.text.toString()
            }
        }

        virtual.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                transaksi.method = virtual.text.toString()
            }
        }

        //address cara 2
        ubahAlamat.setOnClickListener {

            var layout = layoutInflater.inflate(R.layout.dialog_shop_checkout_address,null)
            var dialog = AlertDialog.Builder(this).apply{
                setView(layout)
                setTitle(getString(R.string.change_address))
            }
            var creator = dialog.create()
            var address = layout.findViewById<EditText>(R.id.tbAddress)
            var province = layout.findViewById<EditText>(R.id.tbProvince)
            var city = layout.findViewById<EditText>(R.id.tbCity)
            var postal = layout.findViewById<EditText>(R.id.tbPostal)
            var done = layout.findViewById<Button>(R.id.saveAddress)
            var cancel = layout.findViewById<Button>(R.id.cancelAddress)

            if(singletonData.accList[singletonData.currentAccId].shippingAddress.size != 0){
                address.setText(singletonData.accList[singletonData.currentAccId].shippingAddress[0])
                city.setText(singletonData.accList[singletonData.currentAccId].shippingAddress[1])
                province.setText(singletonData.accList[singletonData.currentAccId].shippingAddress[2])
                postal.setText(singletonData.accList[singletonData.currentAccId].shippingAddress[3])
            }

            done.setOnClickListener {
                if(address.text.toString().equals("")
                    || province.text.toString().equals("")
                    || city.text.toString().equals("")
                    || postal.text.toString().equals("")
                ){
                    Toast.makeText(this,getString(R.string.need_all_filled),Toast.LENGTH_SHORT).show()
                }
                else{
                    user.shippingAddress = mutableListOf<String>(address.text.toString(), city.text.toString(), province.text.toString(), postal.text.toString())
                    singletonData.accList[singletonData.currentAccId].shippingAddress = user.shippingAddress
                    transaksi.address = user.shippingAddress
                    alamat.setText(formatAlamat(transaksi.address))
                    Toast.makeText(this,getString(R.string.success_update_address),Toast.LENGTH_SHORT).show()
                    creator.cancel()
                }
            }

            cancel.setOnClickListener {
                creator.cancel()
            }

            creator.show()
        }

        //phoneNumber cara 1
        ubahTelp.setOnClickListener {
            var layout = layoutInflater.inflate(R.layout.dialog_shop_checkout_phone,null)
            var dialog = AlertDialog.Builder(this).apply{
                setView(layout)
                setTitle(getString(R.string.change_phone))
            }
            var creator = dialog.create()

            var phone = layout.findViewById<EditText>(R.id.tbPhoneNumber)
            var done = layout.findViewById<Button>(R.id.savePhone)
            var cancel = layout.findViewById<Button>(R.id.cancelPhone)

            //Code here
            if(!singletonData.accList[singletonData.currentAccId].phoneNumber.equals("")){
                phone.setText(singletonData.accList[singletonData.currentAccId].phoneNumber)
            }

            done.setOnClickListener {
                if(phone.text.toString().equals("")){
                    Toast.makeText(this,getString(R.string.need_phone_filled),Toast.LENGTH_SHORT).show()
                }
                else{
                    user.phoneNumber = phone.text.toString()
                    singletonData.accList[singletonData.currentAccId].phoneNumber = user.phoneNumber
                    transaksi.phoneNumber = user.phoneNumber
                    telp.setText(user.phoneNumber)
                    Toast.makeText(this,getString(R.string.success_update_phone),Toast.LENGTH_SHORT).show()
                    creator.cancel()
                }
            }

            cancel.setOnClickListener{
                creator.cancel()
            }

            creator.show()

        }

        var ongkos : Int = 5000
        transaksi.subTotal = singletonData.subtotalInCart()
        subtotal.setText("Rp." +singletonData.formatHarga(transaksi.subTotal))

        transaksi.shippingCost = ongkos
        ongkir.setText("Rp." +singletonData.formatHarga(transaksi.shippingCost))

        transaksi.Total = singletonData.totalInCart()
        total.setText("Rp." +singletonData.formatHarga(transaksi.Total))

        checkout.setOnClickListener {

            if(transaksi.method.equals("")
                || transaksi.address.equals("")
                || transaksi.phoneNumber.equals("")){
                Toast.makeText(this, getString(R.string.need_all_filled),Toast.LENGTH_LONG).show()
            }
            else{
                singletonData.accList[singletonData.currentAccId].cartContent.clear()
                var toHistory = Intent(this, shop_tracker::class.java)
                singletonData.accList[singletonData.currentAccId].transactionHistory.add(transaksi)
                toHistory.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(toHistory)
            }
        }
    }

    fun formatAlamat(alamat : MutableList<String>) = "${alamat[0]}, ${alamat[1]}, ${alamat[2]}, ID ${alamat[3]}"

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}