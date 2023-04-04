package com.example.weather.domain.useCases

import com.example.weather.domain.repository.WeatherRepository

class GetHumidityUseCaseprivate(private val repository: WeatherRepository) {
    suspend fun execute(): Double {
        var humidityList: MutableList<Int> = mutableListOf()
        repository.getData().forEach {
            humidityList.add(it.humidity)
        }
        return humidityList.average()
    }
}