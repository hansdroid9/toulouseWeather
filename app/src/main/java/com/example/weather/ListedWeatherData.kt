package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.data.room.WeatherEntity
import com.example.weather.databinding.MoreWeatherDataItemBinding
import com.example.weather.presentation.recyclerView.RecyclerMoreWeatherAdapter
import com.example.weather.presentation.recyclerView.RecyclerWeatherAdapter

class ListedWeatherData : AppCompatActivity() {
    lateinit var binding: MoreWeatherDataItemBinding
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listed_weather_data)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_listed_weather_data)
        val weather = intent.extras?.get("weather") as List<WeatherEntity>
        //Log.i("weatherIntent",weather[0].tempC.toString())
        recyclerView=findViewById(R.id.RecyclerMoreWeather)
        val lineareLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        recyclerView.layoutManager = lineareLayoutManager
        recyclerView.adapter = RecyclerMoreWeatherAdapter(weather)

    }
}