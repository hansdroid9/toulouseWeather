package com.example.weather.data.model


import com.google.gson.annotations.SerializedName

data class Astro(
    @SerializedName("is_moon_up")
    val isMoonUp: Int,
    @SerializedName("is_sun_up")
    val isSunUp: Int
)