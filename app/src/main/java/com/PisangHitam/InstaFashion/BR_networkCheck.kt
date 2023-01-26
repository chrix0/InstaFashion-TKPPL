package com.PisangHitam.InstaFashion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

@Suppress("DEPRECATION")
class BR_networkCheck : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Ketika menerima intent broadcast..
        if (!connected(context, false)){
            Toast.makeText(context, "No internet connection!", Toast.LENGTH_LONG).show()
            var intent = Intent(context, noNetworkDetected::class.java)
            context.startActivity(intent)
        }
    }

    fun connected(context: Context, showToast: Boolean) : Boolean{
        val cm : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Periksa apakah wifi atau mobile data hidup
        val active = cm.activeNetworkInfo
        if (active != null && active.isConnected){
            if (showToast) showToastActive(context, active.type)
            return true
        }
        return false
    }

    fun showToastActive(context: Context, type : Int){
        when(type){
            ConnectivityManager.TYPE_WIFI -> {
                Toast.makeText(context, "Wifi detected!", Toast.LENGTH_SHORT).show()
            }
            ConnectivityManager.TYPE_MOBILE -> {
                Toast.makeText(context, "Mobile data detected!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}