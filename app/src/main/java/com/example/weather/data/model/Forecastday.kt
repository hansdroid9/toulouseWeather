package com.example.weather.data.model


import com.google.gson.annotations.SerializedName

data class Forecastday(
    @SerializedName("astro")
    val astro: Astro,
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    val day: Day,
    @SerializedName("hour")
    val hour: List<Hour>
)