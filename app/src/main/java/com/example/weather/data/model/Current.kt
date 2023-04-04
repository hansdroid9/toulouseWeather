package com.example.weather.data.model


import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("uv")
    val uv: Double
)