package com.example.weather.domain.useCases

import com.example.weather.domain.repository.WeatherRepository

class GetWindVelocityUseCases(private val repository: WeatherRepository) {
    suspend fun execute(): Double {
        var windList:MutableList<Double> = mutableListOf()
        repository.getData().forEach {
            windList.add(it.windKph)
        }
        return windList.average()
    }
}