package com.PisangHitam.InstaFashion

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.PisangHitam.InstaFashion.BR_SettingsChanged.BR_productRecChanged
import com.PisangHitam.InstaFashion.MusicPlayer.musicService
import com.PisangHitam.InstaFashion.singletonData.myMediaPlayer
import com.PisangHitam.InstaFashion.singletonData.playerCreated
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlinx.android.synthetic.main.activity_profile_settings.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.sdk27.coroutines.onClick

class profile_settings : AppCompatActivity(), RewardedVideoAdListener {

    var counter = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        val actionbar = supportActionBar
        actionbar!!.title = "Settings"
        actionbar.setDisplayHomeAsUpEnabled(true)

        //Recommendation Settings
        recNotifSwitch.isChecked = singletonData.mAlarmManager != null

        recNotifSwitch.setOnCheckedChangeListener { button, b ->
            if(b){
                recNotifSettingTrue()
            }
            else{
                recNotifSettingFalse()
            }
        }

        //Media Player
        if (myMediaPlayer?.isPlaying == true){
            player_play.isChecked = true
        }
        var playerIntent = Intent(this, musicService::class.java)
        if(!playerCreated){
            playerCreated = true
            playerIntent?.action = ACTION_CREATE
            startService(playerIntent)
        }

        player_play.onCheckedChange { buttonView, isChecked ->
            if(isChecked){
                playerIntent.setAction(ACTION_PLAY)
                startService(playerIntent)
            }
        }

         player_pause.onCheckedChange { buttonView, isChecked ->
             if(isChecked){
                 playerIntent.setAction(ACTION_PAUSE)
                 startService(playerIntent)
             }
         }

        player_stop.onCheckedChange { buttonView, isChecked ->
            if(isChecked){
                playerIntent.setAction(ACTION_STOP)
                startService(playerIntent)
            }
        }

        MobileAds.initialize(this, "ca-app-pub-3303024751964337~5073638132")
        var adRequires = AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build()

        //BANNER
        adView.loadAd(adRequires)

        //INTERSTITIAL
        showInterstitial()

        //REWARD
        loadReward()
    }

    fun loadReward(){
        var adRequires = AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build()

        var mRewardedVideoAd: RewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
            adRequires)

        showRewardAd.setOnClickListener {
            mRewardedVideoAd.show()
        }
    }

    fun showInterstitial(){
        var mInterstitialAd = singletonData.mInterstitialAd
        if(mInterstitialAd!!.isLoaded)
            mInterstitialAd!!.show()
        else{
            Toast.makeText(this, "Interstitial ad wasn't loaded", Toast.LENGTH_SHORT).show()
            singletonData.loadInterstitial(this)
        }
    }

    //RECOMMENDATION SETTINGS TOGGLE
    @RequiresApi(Build.VERSION_CODES.O)
    fun recNotifSettingFalse(){
        singletonData.mAlarmManager?.cancel(singletonData.mPendingIntent)
        singletonData.mPendingIntent?.cancel()
        singletonData.mAlarmManager = null
        singletonData.mPendingIntent = null

        //Notifikasi Undo
        val NotifyId = NOTIF_PRODUCTREC_CHANGED
        val Channel_id = "channel_recs_change"
        val name = "ProductRecommendationChangeAlert"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val NotifyChannel : NotificationChannel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(Channel_id, name, importance).apply {
                //Dot Notification
                description = "Product Recommendations Disabled"
                setShowBadge(true)
            }
        } else {
            Toast.makeText(this, "Unable to show notification.", Toast.LENGTH_LONG).show()
            null
        }

        val undoIntent = Intent(applicationContext, BR_productRecChanged::class.java)
        undoIntent.action = "Undo"
        val undoPendingIntent = PendingIntent.getBroadcast(applicationContext, 0, undoIntent,0)

        var mBuilder = NotificationCompat.Builder(this, Channel_id)
            .setSmallIcon(R.drawable.logo01)
            .setContentText("Product recommendation notifications has been disabled.")
            .setContentTitle("Notification disabled")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .addAction(R.drawable.icon_undo, "Undo", undoPendingIntent)

        var mNotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (NotifyChannel != null) {
            mNotificationManager.createNotificationChannel(NotifyChannel)
        }

        mNotificationManager.notify(NotifyId, mBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun recNotifSettingTrue(){
        var sendIntent = Intent(this, BR_recNotifier::class.java)
        var alarmTimer = Calendar.getInstance()
        alarmTimer.add(Calendar.SECOND, 15)

        singletonData.mAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        singletonData.mPendingIntent = PendingIntent.getBroadcast(this, 200, sendIntent, 0 )
        singletonData.mAlarmManager!!.setInexactRepeating(AlarmManager.RTC, alarmTimer.timeInMillis, AlarmManager.INTERVAL_FIFTEEN_MINUTES,singletonData.mPendingIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun update(recNotifierState:Boolean){
        recNotifSwitch.isChecked = recNotifierState
    }

    override fun onRewardedVideoAdLoaded() {
        showRewardAd.visibility = View.VISIBLE
    }

    override fun onRewardedVideoAdOpened() {
    }

    override fun onRewardedVideoStarted() {
    }

    override fun onRewardedVideoAdClosed() {
    }

    override fun onRewarded(p0: RewardItem?) {
        counter += p0!!.amount
        countWatch.text = "Watch points : ${counter.toString()}"
        loadReward()
    }

    override fun onRewardedVideoAdLeftApplication() {
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
    }

    override fun onRewardedVideoCompleted() {
    }
}