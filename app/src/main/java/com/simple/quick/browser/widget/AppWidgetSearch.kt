package com.simple.quick.browser.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.simple.quick.browser.R
import com.simple.quick.browser.ui.activity.MainActivity

class AppWidgetSearch: AppWidgetProvider() {

    companion object {
        const val ACTION = "com.simple.quick.browser.widget.action.AppWidgetSearch"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        println("onReceive -> $intent")
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        println("onEnabled")
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        println("onDeleted")
    }

    @SuppressLint("RemoteViewLayout")
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        println("onUpdate")
        appWidgetIds.forEach {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            val remoteViews = RemoteViews(context.packageName, R.layout.appwidget_search)
            remoteViews.setOnClickPendingIntent(R.id.view, pendingIntent)
            appWidgetManager.updateAppWidget(it, remoteViews)
        }
    }
}