package com.example.weather.data.model


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("cloud")
    val cloud: Int,
    @SerializedName("condition")
    val condition: ConditionXX,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("time")
    val time: String,
    @SerializedName("time_epoch")
    val timeEpoch: Int,
    @SerializedName("uv")
    val uv: Double,
    @SerializedName("will_it_rain")
    val willItRain: Int,
    @SerializedName("will_it_snow")
    val willItSnow: Int,
    @SerializedName("wind_kph")
    val windKph: Double
)