package com.example.weather.domain.useCases

import com.example.weather.data.room.WeatherEntity
import com.example.weather.domain.repository.WeatherRepository

class UpdateForecastDataUsesCase(private val repository: WeatherRepository) {
    suspend fun execute():List<WeatherEntity>{
        return repository.updateData()
    }
}