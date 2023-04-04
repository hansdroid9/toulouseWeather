package com.example.weather.domain.repository

import com.example.weather.data.room.WeatherEntity

interface WeatherRepository {
    suspend fun getData():List<WeatherEntity>
    suspend fun updateData(): List<WeatherEntity>
}