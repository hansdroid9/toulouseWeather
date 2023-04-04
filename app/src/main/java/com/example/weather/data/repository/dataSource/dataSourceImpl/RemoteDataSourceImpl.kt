package com.example.weather.data.repository.dataSource.dataSourceImpl

import com.example.weather.data.api.WeatherServices
import com.example.weather.data.model.Weather
import com.example.weather.data.repository.dataSource.RemoteDatasource
import retrofit2.Response

class RemoteDataSourceImpl(private val weatherService: WeatherServices): RemoteDatasource{
    override suspend fun getDataFromRemote(): Response<Weather> {
        return weatherService.getWeathers()
    }
}