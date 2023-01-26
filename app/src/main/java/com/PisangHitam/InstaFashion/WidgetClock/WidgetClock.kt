package com.PisangHitam.InstaFashion.WidgetClock

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.PisangHitam.InstaFashion.R
import com.PisangHitam.InstaFashion.Room.roomHelper
import com.PisangHitam.InstaFashion.SharedPref.dbSharedPref
import com.PisangHitam.InstaFashion.WidgetClock.WidgetClock.Companion.getRandomProduct
import com.PisangHitam.InstaFashion.WidgetClock.WidgetClock.Companion.pendingIntenttoAct
import com.PisangHitam.InstaFashion.classProduk
import com.PisangHitam.InstaFashion.singletonData
import com.PisangHitam.InstaFashion.splash_screen
import kotlin.random.Random

/**
 * Implementation of App Widget functionality.
 */
class WidgetClock : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        if(dbSP ==  null){
            dbSP = dbSharedPref(context)
        }

        db = dbSP?.db
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object{
        var dbSP : dbSharedPref? = null
        var db : roomHelper? = null

        fun getRandomProduct(db : roomHelper) : classProduk {
            var products = db.daoOutfitList().getAllOutfit()
            var jumlahProduct = products.size
            var indexRandom = rand(0, jumlahProduct - 1)
            return products[indexRandom]
        }

        fun rand(start : Int, end: Int) = Random(System.nanoTime()).nextInt(end - start + 1) + start

        fun pendingIntenttoAct(context : Context) : PendingIntent{
            var intent = Intent(context, splash_screen::class.java)
            return PendingIntent.getActivity(context, 99, intent, 0)
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.widget_clock)
    var db = WidgetClock.db

    var chosen = getRandomProduct(db!!)

    views.setTextViewText(R.id.pointlessRec, "Check this out: ${chosen.namaProduk}")
    views.setOnClickPendingIntent(R.id.widget, pendingIntenttoAct(context))

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}


