package com.example.weather.domain.useCases

import com.example.weather.domain.repository.WeatherRepository

class GetMoyenTempUseCases(private val repository: WeatherRepository) {
    suspend fun execute(): Double {
        var tempList:MutableList<Double> = mutableListOf()
        repository.getData().forEach {
            tempList.add(it.tempC)
        }
        return tempList.average()
    }
}