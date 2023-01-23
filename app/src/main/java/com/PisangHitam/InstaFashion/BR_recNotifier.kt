package com.PisangHitam.InstaFashion

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat.getSystemService
import kotlin.random.Random

class BR_recNotifier : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val NotifyId = NOTIF_PRODUCTREC
        val Channel_id = "channel_recs"
        val name = "ProductRecommendation"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        var product = getRandomProduct()
        var notifText = "${product.namaProduk}"
        var notifTitle = "Check this out!"

        var recNotif = classRecNotif(notifText,notifTitle)


        //Activity muncul setelah notifikasi ditekan
        val dest = Intent(context, shop_infoProduk::class.java)
        dest.putExtra(SHOW_PRODUCT_INFO, product)

        val iPending = TaskStackBuilder.create(context)
            .addNextIntentWithParentStack(dest) //Intent
            .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT)

        //Pembentukan notifikasi
        var mBuilder = NotificationCompat.Builder(context, Channel_id)
            .setSmallIcon(R.drawable.logo01)
            .setContentText(notifText)
            .setContentTitle(notifTitle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(iPending) //!!
            .setAutoCancel(true)

        val nNotifyChannel : NotificationChannel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(Channel_id, name, importance).apply {
                //Dot Notification
                description = "Product Recommendations"
                setShowBadge(true)
            }
        } else {
            Toast.makeText(context, "Unable to show notification.", Toast.LENGTH_LONG).show()
            null
        }

        var mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        for(s in mNotificationManager.notificationChannels){
            mNotificationManager.deleteNotificationChannel(s.id)
        }

        //Channel
        if (nNotifyChannel != null) {
            mNotificationManager.createNotificationChannel(nNotifyChannel)
        }
        //Munculkan notifikasi
        mNotificationManager.notify(NotifyId, mBuilder.build())

        var db = singletonData.getRoomHelper(context)
        var user = singletonData.getCurUserObj(context)
        user!!.notifications.add(recNotif)
        db.daoAccount().updateAcc(user)
    }

    fun rand(start : Int, end: Int) = Random(System.nanoTime()).nextInt(end - start + 1) + start

    fun getRandomProduct() : classProduk{
        var products = singletonData.outfitList
        var jumlahProduct = products.size
        var indexRandom = rand(0, jumlahProduct - 1)

        return products[indexRandom]
    }
}