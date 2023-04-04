package com.example.weather.data.api

import com.example.weather.data.model.Weather
import retrofit2.Response
import retrofit2.http.GET

interface WeatherServices {
    @GET("v1/forecast.json?key=463f567aa4f046a0875181637230603&q=Toulouse&days=1&aqi=no&alerts=no")
    suspend fun getWeathers():Response<Weather>
}