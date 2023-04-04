package com.example.weather.presentation.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weather.R

class NotifHourlyWeatherForcast(context: Context, params: WorkerParameters):Worker(context,params) {
    private val channelID= "com.example.weather.channel1"
    private var notificationManager: NotificationManager?= null
    override fun doWork(): Result {

        try {
            notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
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
            val notification = NotificationCompat.Builder(applicationContext,channelID)
                .setContentTitle("Demo Title")
                .setContentText("Salut!\n la température moyenne est ")
                .setSmallIcon(R.drawable.thermostat)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
            notificationManager?.notify(notificationId,notification)
            return Result.success()
        }catch (e:Exception){
            return Result.failure()
        }
    }
}