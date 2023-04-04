package com.example.weather.domain.useCases

import com.example.weather.domain.repository.WeatherRepository

class GetActualTempUsesCase(private val repository: WeatherRepository) {
    suspend fun execute(): Double{
        return repository.getData().get(0).tempC
    }
}