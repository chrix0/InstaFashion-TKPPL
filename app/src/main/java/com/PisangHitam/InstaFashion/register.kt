package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.PisangHitam.InstaFashion.LoginActivity
import com.PisangHitam.InstaFashion.classAccount
import kotlinx.android.synthetic.main.activity_register.*

class register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        lateinit var cuserName : String
        lateinit var cpassword : String
        lateinit var cfullName : String
        lateinit var cemail : String


        var clsAccount = classAccount(
            singletonData.accList.size,
            "TESTACCOUNT",
            "TEST",
            "Test Account",
            "testaccount@gmail.com",
            mutableListOf(),
            mutableListOf("","","",""),
            "",
            mutableListOf()
        )

        fun checkdupe(string : String) : Boolean{
            for(i in singletonData.accList){
                if(string.equals(i.userName)){
                    return true
                }
            }
            return false
        }

        text.setOnClickListener{
            finish()
        }

        signup.setOnClickListener{
            cuserName = username.text.toString()
            cpassword = password.text.toString()
            cfullName = fullname.text.toString()
            cemail = email.text.toString()

            if(cuserName.equals("") || cpassword.equals("") || cfullName.equals("") || cemail.equals("")){
                Toast.makeText(this, "All field needs to be filled.", Toast.LENGTH_SHORT).show()
            }
            else{
                if(checkdupe(cuserName)){
                    Toast.makeText(this, "An account with that username exists. Please use another username.", Toast.LENGTH_SHORT).show()
                }
                else{
                    clsAccount.userName=cuserName
                    clsAccount.password=cpassword
                    clsAccount.fullName=cfullName
                    clsAccount.email=cemail

                    singletonData.accList.add(clsAccount)
                    Toast.makeText(this, "Your account has been successfully created", Toast.LENGTH_SHORT).show()

                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}