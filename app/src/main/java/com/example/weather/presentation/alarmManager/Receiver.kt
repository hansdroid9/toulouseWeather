package com.example.weather.presentation.alarmManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.weather.R

class Receiver:BroadcastReceiver() {
    private val channelID= "com.example.weather.channel1"
    private var notificationManager: NotificationManager?= null
    var meanTemp = "0"
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent!!.action
        Log.i("Receiver", "Broadcast received: $action")
        if (action == "meanTemp") {
            meanTemp = intent!!.extras!!.getString("extra").toString()
        }
        try {
            notificationManager = context?.getSystemService(NotificationManager::class.java)
            // Create a notification channel for Android O and above
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                val importance = NotificationManager.IMPORTANCE_HIGH
                val decription = "this is weather notification"
                val channel = NotificationChannel(channelID,"weatherChannel",importance).apply {
                    description = decription
                }
                notificationManager?.createNotificationChannel(channel)
            }
            val notificationId = 45
            val notification = context?.let {
                NotificationCompat.Builder(it,channelID)
                    .setContentTitle("weather ")
                    .setContentText("La température moyenne de la journée est $meanTemp")
                    .setSmallIcon(R.drawable.thermostat)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()
            }
            notificationManager?.notify(notificationId,notification)
            Log.i("notif", "ça fonctionne")
        }catch (e:Exception){
            Log.i("notif", e.toString())
        }
    }
}