package com.example.weather.data.model


import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("totalsnow_cm")
    val totalsnowCm: Double
)