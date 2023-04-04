package com.example.weather.data.repository.dataSource.dataSourceImpl

import com.example.weather.data.repository.dataSource.LocalDataSource
import com.example.weather.data.room.WeatherDAO
import com.example.weather.data.room.WeatherEntity

class LocalDataSourceImpl(private val dao: WeatherDAO): LocalDataSource {
    override suspend fun getdataFromdb(): List<WeatherEntity> {
        return dao.getWeather()
    }

    override suspend fun savedataTodb(weatherList:List<WeatherEntity>) {
        dao.addWeather(weatherList)
    }

    override suspend fun DeleteAll() {
        dao.deleteAllForeCast()
    }
}