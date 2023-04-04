package com.example.weather.data.room

import androidx.room.*
import com.example.weather.data.model.Weather

@Dao
interface WeatherDAO {
    @Query(" SELECT * FROM weather_table")
    suspend fun getWeather():List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeather(weather: List<WeatherEntity>)

    @Query("DELETE FROM weather_table")
    suspend fun deleteAllForeCast()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWeather(weather: WeatherEntity)
}