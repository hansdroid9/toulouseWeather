package com.example.weather.alarmManager

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.weather.R
import java.util.*


class Alarm : BroadcastReceiver() {
    private val channelID= "com.example.weather.channel1"
    private var notificationManager: NotificationManager?= null
    override fun onReceive(context: Context, intent: Intent?) {
        val wakeLock: PowerManager.WakeLock =
            (context.getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag").apply {
                    acquire()
                }
            }

        try {
            notificationManager = context.getSystemService(NotificationManager::class.java)
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
            val notification = NotificationCompat.Builder(context,channelID)
                .setContentTitle("Demo Title")
                .setContentText("This is a demo notification")
                .setSmallIcon(R.drawable.thermostat)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
            notificationManager?.notify(notificationId,notification)
        }catch (e:Exception){
            Log.i("notification",e.toString())
        }
        Toast.makeText(context, "Notification sended", Toast.LENGTH_LONG).show() // For example
        wakeLock.release()
    }

    fun setAlarm(context: Context) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(context, Alarm::class.java)
        val pi = PendingIntent.getBroadcast(context, 0, i, 0)

        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(System.currentTimeMillis())
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

    //    calendar.setTimeInMillis(System.currentTimeMillis())
    //    calendar.set(Calendar.HOUR_OF_DAY, 7)
    //    calendar.set(Calendar.MINUTE, 0)
    //    calendar.set(Calendar.SECOND, 0)

        am.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_DAY,
            pi
        )
    }

    fun cancelAlarm(context: Context) {
        val intent = Intent(context, Alarm::class.java)
        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(sender)
    }
}