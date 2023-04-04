package com.example.weather.data.model


import com.google.gson.annotations.SerializedName

data class ConditionXX(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("text")
    val text: String
)