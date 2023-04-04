package com.example.weather.data.repository.dataSource

import com.example.weather.data.room.WeatherEntity

interface LocalDataSource {
    suspend fun getdataFromdb():List<WeatherEntity>
    suspend fun savedataTodb(weatherList:List<WeatherEntity>)
    suspend fun DeleteAll()
}