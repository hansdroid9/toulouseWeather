package com.example.weather.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.data.model.Weather

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase(){
    abstract val weatherDao:WeatherDAO

    companion object{
        private var INSTANCE:WeatherDatabase?=null
        fun getInstance(context: Context):WeatherDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        WeatherDatabase::class.java,
                        "weather_data_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}


