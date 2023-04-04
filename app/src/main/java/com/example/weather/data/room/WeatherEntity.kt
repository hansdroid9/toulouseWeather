package com.example.weather.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.data.model.ConditionXX
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "weather_table")
class WeatherEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subsciber_id")
    var id:Int,
    @SerializedName("cloud")
    var cloud: Int,
    @SerializedName("conditionIcon")
    var conditionIcon: String,
    @SerializedName("conditionText")
    var conditionText: String,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("is_day")
    var isDay: Int,
    @SerializedName("precip_mm")
    var precipMm: Double,
    @SerializedName("temp_c")
    var tempC: Double,
    @SerializedName("time")
    var time: String,
    @SerializedName("time_epoch")
    var timeEpoch: Int,
    @SerializedName("uv")
    var uv: Double,
    @SerializedName("will_it_rain")
    var willItRain: Int,
    @SerializedName("will_it_snow")
    var willItSnow: Int,
    @SerializedName("wind_kph")
    var windKph: Double,
): Serializable