package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_personaldata.*

class Personaldata : AppCompatActivity() {

    private val EXTRA_NAME = "NAME"
    private val EXTRA_EM = "EMAIL"
    private val EXTRA_ADD = "ADDRESS"
    private val EXTRA_CITY = "CITY"
    private val EXTRA_PROV = "PROVINCE"
    private val EXTRA_POST = "POSTAL_CODE"
    private val EXTRA_PHONE = "PHONE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaldata)

        val actionbar = supportActionBar
        actionbar!!.title = "Your personal data"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val db = singletonData.getRoomHelper(this)

        var user : classAccount? = if(intent.hasExtra(dummy_for_test)){
            intent.getParcelableExtra<classAccount>(dummy_for_test)
        } else{
            singletonData.getCurUserObj(this)
        }

        name.setText(user!!.fullName)
        email.setText(user.email)
        address.setText(user.shippingAddress[0])
        City.setText(user.shippingAddress[1])
        Province.setText(user.shippingAddress[2])
        psCode.setText(user.shippingAddress[3])
        Pnumber.setText(user.phoneNumber)

        save.setOnClickListener{
            user.fullName = name.text.toString()
            user.email = email.text.toString()
            user.shippingAddress = mutableListOf<String>(address.text.toString(), City.text.toString(),Province.text.toString(),psCode.text.toString())
            user.phoneNumber = Pnumber.text.toString()
            db.daoAccount().updateAcc(user)
            var intent = Intent(this,MainActivity::class.java)
            intent.putExtra(RETURN_LAST_TAB, "PROFILE")
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_NAME, name.text.toString())
        outState.putString(EXTRA_EM, email.text.toString())
        outState.putString(EXTRA_ADD, address.text.toString())
        outState.putString(EXTRA_CITY, City.text.toString())
        outState.putString(EXTRA_PROV, Province.text.toString())
        outState.putString(EXTRA_POST, psCode.text.toString())
        outState.putString(EXTRA_PHONE, Pnumber.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        name.setText(savedInstanceState?.getString(EXTRA_NAME, ""))
        email.setText(savedInstanceState?.getString(EXTRA_EM, ""))
        address.setText(savedInstanceState?.getString(EXTRA_ADD, ""))
        City.setText(savedInstanceState?.getString(EXTRA_CITY, ""))
        Province.setText(savedInstanceState?.getString(EXTRA_PROV, ""))
        psCode.setText(savedInstanceState?.getString(EXTRA_POST, ""))
        Pnumber.setText(savedInstanceState?.getString(EXTRA_PHONE, ""))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}