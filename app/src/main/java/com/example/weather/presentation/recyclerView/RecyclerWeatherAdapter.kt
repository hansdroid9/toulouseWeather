package com.example.weather.presentation.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.data.room.WeatherEntity
import com.example.weather.presentation.utils.utils

class RecyclerWeatherAdapter(private val weather:List<WeatherEntity>) : RecyclerView.Adapter<WeatherViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val weatherItem = layoutInflater.inflate(R.layout.recycler_item_forecast,parent,false)
        return WeatherViewHolder(weatherItem)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        //holder.weatherImageStatus.resources = utils.getIconRessource(weather.get(position))
        holder.hourTime.text = position.toString() // set l'heure par rapport à la position de la liste
        holder.tempValue.text = weather.get(position).tempC.toString()
        // set the ressources
        utils.setImageResource(holder.weatherImageStatus, utils.getIconRessource(weather.get(position)))
        if(position<=12){
            holder.am_pm.text = "AM"
        }else{
            holder.am_pm.text = "PM"
        }
    }

    override fun getItemCount(): Int {
        return 24
    }



}

class WeatherViewHolder(val view: View):RecyclerView.ViewHolder(view){
    var weatherImageStatus = view.findViewById<ImageView>(R.id.weatherStatusImage)
    var hourTime = view.findViewById<TextView>(R.id.hourValueText)
    var tempValue = view.findViewById<TextView>(R.id.temperatureValue)
    var am_pm = view.findViewById<TextView>(R.id.AM_PM_text)
}