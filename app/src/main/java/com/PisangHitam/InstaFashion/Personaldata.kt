package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_personaldata.*

class Personaldata : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaldata)

        var user = singletonData.accList[singletonData.currentAccId]

        name.setText(user.fullName)
        email.setText(user.email)
        address.setText(user.shippingAddress[0])
        City.setText(user.shippingAddress[1])
        Province.setText(user.shippingAddress[2])
        psCode.setText(user.shippingAddress[3])
        Pnumber.setText(user.phoneNumber)

        save.setOnClickListener{
            singletonData.accList[singletonData.currentAccId].fullName = name.text.toString()
            singletonData.accList[singletonData.currentAccId].email = email.text.toString()
            user.shippingAddress = mutableListOf<String>(address.text.toString(), City.text.toString(),Province.text.toString(),psCode.text.toString())
            singletonData.accList[singletonData.currentAccId].phoneNumber = Pnumber.text.toString()
            var intent = Intent(this,MainActivity::class.java)
            intent.putExtra(RETURN_LAST_TAB, "PROFILE")
            startActivity(intent)
        }


    }
}