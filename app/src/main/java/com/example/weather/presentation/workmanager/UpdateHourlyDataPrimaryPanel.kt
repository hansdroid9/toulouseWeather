package com.example.weather.presentation.workmanager

import android.content.Context
import android.content.ContextParams
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.String.format
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class UpdateHourlyDataPrimaryPanel(context: Context,params: WorkerParameters):Worker(context,params) {
    override fun doWork(): Result {
        try {
            for (i in 0..3){
                Log.i("mytag","je compte compte $i")
            }
            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val curentDate =time.format(Date())
            Log.i("mytag","il est $curentDate")
            return Result.success()
        } catch (e:Exception){
            return Result.failure()
        }

    }
}