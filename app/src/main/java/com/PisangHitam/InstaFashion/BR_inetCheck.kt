package com.PisangHitam.InstaFashion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class BR_inetCheck : BroadcastReceiver() {
    private var player : MediaPlayer? = null

    override fun onReceive(context: Context, intent: Intent) {
        var connected = intent?.getBooleanExtra(INTERNET_CONNECTED, false)

        if(!connected){
            player = MediaPlayer.create(context, R.raw.se_faulty)
            Toast.makeText(context, "Connection timeout! Please check your internet connection.", Toast.LENGTH_SHORT).show()
        }
        else{
            player = MediaPlayer.create(context, R.raw.se_ok)
            Toast.makeText(context, "Internet found.", Toast.LENGTH_SHORT).show()
        }
        player?.start()
    }
}