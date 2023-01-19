package com.PisangHitam.InstaFashion

import android.content.Intent
import android.database.Cursor
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.Parcelable
import android.widget.Toast
import com.PisangHitam.InstaFashion.SharedPref.loginSharedPref
import com.PisangHitam.InstaFashion.realtimeDB.fbDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val EXTRA_USER = "USER"
    private val EXTRA_PASS = "PASSWORD"

    private var sp : SoundPool? = null
    private var soundID = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var db = singletonData.getRoomHelper(applicationContext)
        button2.setOnClickListener{
            var userName = usernameLogin.text.toString()
            var password = passwordLogin.text.toString()

            if (userName.isEmpty()||password.isEmpty()) {
                Toast.makeText(this, "Please fill in username and password.", Toast.LENGTH_SHORT).show()
            }
            else {
                var found = false
                var getAccount = db.daoAccount().getAcc(userName, password)
                if (getAccount.isNotEmpty()) {
                    found = true
                    singletonData.currentAccId = getAccount[0].id
                }
                var fbDb = fbDatabase(this)
                var fbFound = fbDb.foundAcc(singletonData.fbUpdated, userName, password)
                if(found and fbFound){
                    Toast.makeText(this, "Successfully logged in.", Toast.LENGTH_SHORT).show()
                    var sharedpref = loginSharedPref(this)
                    if(keepLoggedIn.isChecked){
                        sharedpref.idUser = getAccount[0].id
                    }
                    var intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    sp?.play(soundID,0.99f, 0.99f, 1, 0, 0.99f)

                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Account is not found. Please sign up.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        toSignUp.setOnClickListener{
            var intent = Intent(this, register::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        //Di sini cocok digunakan untuk melakukan load data..
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            createNewSoundPool()
        }
        else{
            createOldSoundPool()
        }

//        sp?.setOnLoadCompleteListener{soundPool, id, status ->
//            if (status != 0){
//                //Status yang bukan 0 berarti ada masalah dalam proses loading
//            }
//            else{
//            }
//        }

        //load(context, lagu raw, priority)
        //lebih kecil nilai priority lebih tinggi prioritasnya
        soundID = sp?.load(this, R.raw.se_loginok, 1) ?: 0
    }

    //Fungsi createNewSoundPool untuk yang SDK lollipop dan atasnya
    private fun createNewSoundPool(){
        sp = SoundPool.Builder().setMaxStreams(15).build()
    }

    //Fungsi createNewSoundPool untuk yang di bawah lollipop
    private fun createOldSoundPool(){
        sp = SoundPool(15, AudioManager.STREAM_MUSIC, 0) //Max streams, ??, kualitas suara
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_USER, usernameLogin.text.toString())
        outState.putString(EXTRA_PASS, passwordLogin.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        usernameLogin.setText(savedInstanceState?.getString(EXTRA_USER,""))
        passwordLogin.setText(savedInstanceState?.getString(EXTRA_PASS,""))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(singletonData.nw_receiver)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(singletonData.nw_receiver, singletonData.nw_filter)
    }
}





