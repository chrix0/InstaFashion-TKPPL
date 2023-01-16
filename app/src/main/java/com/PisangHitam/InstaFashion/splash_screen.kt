package com.PisangHitam.InstaFashion

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.room.Room
import com.PisangHitam.InstaFashion.LoginActivity
import com.PisangHitam.InstaFashion.Room.roomHelper
import com.PisangHitam.InstaFashion.SharedPref.loginSharedPref
import com.PisangHitam.InstaFashion.locChecker.js_getGeo
import com.PisangHitam.InstaFashion.realtimeDB.fbDatabase
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

@Suppress("Deprecation")
class splash_screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        singletonData.nw_filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)

        //Start JobScheduler
        startGetGeo()
        //Mengambil data dari API pertama kali
        var geoGet = js_getGeo()
        geoGet.getGeoFirst(geoGet.getIP()!!)
        
        //Dengan library anko
        doAsync{
            for(i in 0..1500){
                uiThread {
                    progBar.progress += 1
                }
                Thread.sleep(1)
                 //Beri jeda 1ms untuk setiap iterasi
            }

            //Thread.sleep(2000)
            uiThread {
                var db = singletonData.getRoomHelper(baseContext)
                //!!!
                db.daoAccount().addAllAcc(singletonData.accList)

                var intent : Intent
                if (singletonData.nw_receiver.connected(this@splash_screen, true)) {
                    var sharedpref = loginSharedPref(this@splash_screen)
                    if(sharedpref.idUser == -1){
                        intent = Intent(this@splash_screen, LoginActivity::class.java)
                    }
                    else{
                        var db = singletonData.getRoomHelper(applicationContext)
                        var getAccount = db.daoAccount().getAccById(sharedpref.idUser!!)
                        if (getAccount.isNotEmpty()) {
                            singletonData.currentAccId = getAccount[0].id
                        }
                        intent = Intent(this@splash_screen, MainActivity::class.java)
                    }

                    singletonData.loadInterstitial(applicationContext)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }

            var fbDb = fbDatabase(applicationContext)
        }

        /*
        Handler().postDelayed(Runnable{
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
         */
    }

    override fun onBackPressed() {

    }



    override fun onStop() {
        super.onStop()
        unregisterReceiver(singletonData.nw_receiver)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(singletonData.nw_receiver, singletonData.nw_filter)
    }

    fun startGetGeo(){
        var JobSchedulerId = SCHEDULER_GET_GEO
        var serviceComponent = ComponentName(this, js_getGeo::class.java)

        var mJobInfo = JobInfo.Builder(JobSchedulerId, serviceComponent)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setRequiresDeviceIdle(false)
            .setRequiresCharging(false)
            .setPeriodic(15*60*1000) // setPeriodic minimal 15 menit.

        var GeoJob = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        GeoJob.schedule(mJobInfo.build())
    }
}
