package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.PisangHitam.InstaFashion.LoginActivity
import com.PisangHitam.InstaFashion.classAccount
import com.PisangHitam.InstaFashion.realtimeDB.fbDatabase
import kotlinx.android.synthetic.main.activity_register.*

class register : AppCompatActivity() {

    private val EXTRA_REG_USER = "USER"
    private val EXTRA_REG_PASS = "PASS"
    private val EXTRA_REG_FN = "FULLNAME"
    private val EXTRA_REG_EM = "EMAIL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        lateinit var cuserName : String
        lateinit var cpassword : String
        lateinit var cfullName : String
        lateinit var cemail : String

        var db = singletonData.getRoomHelper(applicationContext)

        var clsAccount = classAccount(
            0,
            "TESTACCOUNT",
            "TEST",
            "Test Account",
            "testaccount@gmail.com",
            mutableListOf(),
            mutableListOf("","","",""),
            "",
            mutableListOf(),
            mutableListOf(),
            mutableListOf()
        )

        fun checkdupe(string : String) : Boolean{
            var getAccount = db.daoAccount().getAccUserCheck(string)
            if (getAccount.isNotEmpty()) {
                return true
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

//                    singletonData.accList.add(clsAccount)

                    var fbDb = fbDatabase(this)

                    if (!fbDb.foundAcc(singletonData.fbUpdated, cuserName, cpassword)){
                        fbDb.saveAcc(cuserName, cpassword)
                        db.daoAccount().newAcc(clsAccount)
                        Toast.makeText(this, "Your account has been successfully created", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "another account existed", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_REG_USER, username.text.toString())
        outState.putString(EXTRA_REG_PASS, password.text.toString())
        outState.putString(EXTRA_REG_FN, fullname.text.toString())
        outState.putString(EXTRA_REG_EM, email.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        username.setText(savedInstanceState?.getString(EXTRA_REG_USER,""))
        password.setText(savedInstanceState?.getString(EXTRA_REG_PASS,""))
        fullname.setText(savedInstanceState?.getString(EXTRA_REG_FN,""))
        email.setText(savedInstanceState?.getString(EXTRA_REG_EM,""))
    }
}