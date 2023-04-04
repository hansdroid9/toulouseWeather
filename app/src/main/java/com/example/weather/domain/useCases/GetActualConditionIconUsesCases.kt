package com.example.weather.domain.useCases

import com.example.weather.domain.repository.WeatherRepository

class GetActualConditionIconUsesCases(private val weatherRepository: WeatherRepository) {
    suspend fun execute():String{
        return weatherRepository.getData().get(0).conditionIcon
    }
}