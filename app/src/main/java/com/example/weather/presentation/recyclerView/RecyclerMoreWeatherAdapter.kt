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

class RecyclerMoreWeatherAdapter(private val weather:List<WeatherEntity>) : RecyclerView.Adapter<MoreWeatherViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreWeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val weatherItem = layoutInflater.inflate(R.layout.more_weather_data_item,parent,false)
        return MoreWeatherViewHolder(weatherItem)
    }

    override fun onBindViewHolder(holder: MoreWeatherViewHolder, position: Int) {
        //holder.weatherImageStatus.resources = utils.getIconRessource(weather.get(position))
        holder.hourTime.text = position.toString() // set l'heure par rapport à la position de la liste
        holder.tempValue.text = weather.get(position).tempC.toString()
        holder.indiceUv.text = weather.get(position).uv.toString()
        holder.humidityValue.text = weather.get(position).humidity.toString()
        // set if it will rain or not
        if(weather.get(position).willItRain == 1){
            holder.willItRain.text ="RAIN"
            holder.willItRain.alpha = 1.0f
        }else {
            holder.willItRain.text ="NO RAIN"
            holder.willItRain.alpha = 0.5f
        }
        holder.windVelocity.text = weather.get(position).windKph.toString()
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

class MoreWeatherViewHolder(val view: View): RecyclerView.ViewHolder(view){
    var weatherImageStatus = view.findViewById<ImageView>(R.id.MW_imageStatus)
    var hourTime = view.findViewById<TextView>(R.id.heureItem)
    var tempValue = view.findViewById<TextView>(R.id.degreItem)
    var am_pm = view.findViewById<TextView>(R.id.am_pm_MW)
    var indiceUv = view.findViewById<TextView>(R.id.indiceUvItem)
    var humidityValue = view.findViewById<TextView>(R.id.humidityItem)
    var willItRain = view.findViewById<TextView>(R.id.rainItemIndicator)
    var windVelocity = view.findViewById<TextView>(R.id.windVelocityItem)
}