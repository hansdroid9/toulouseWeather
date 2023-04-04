package com.example.weather.data.repository.dataSource

import com.example.weather.data.api.WeatherServices
import com.example.weather.data.model.Weather
import retrofit2.Response

interface RemoteDatasource {
    suspend fun getDataFromRemote():Response<Weather>
}