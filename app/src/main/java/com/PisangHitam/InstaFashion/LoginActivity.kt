package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rain.adopets.register
import com.rain.adopets.singletonData
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button2.setOnClickListener{
            var userName = usernameLogin.text.toString()
            var password = passwordLogin.text.toString()

            if (userName.isEmpty()||password.isEmpty()) {
                Toast.makeText(this, "Please fill in username and password.", Toast.LENGTH_SHORT).show()
            }
            else {
                var found = false
                for ((index, i ) in singletonData.accList.withIndex()) {
                    if (userName == i.userName && password == i.password) {
                        found = true
                        singletonData.currentAccId = i.id
                    }
                }

                if(found){
                    Toast.makeText(this, "Successfully logged in.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Account is not found. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        toSignUp.setOnClickListener{
            var intent = Intent(this, register::class.java)
            startActivity(intent)
        }
    }
}