package com.example.weather.domain.useCases

import com.example.weather.domain.repository.WeatherRepository
import com.github.mikephil.charting.data.Entry

class GetDataForPlotChart(private val repository: WeatherRepository) {
    suspend fun execute():List<Entry>{
        val list = mutableListOf<Entry>()
        if(repository.getData().size >= 23 ){
            repository.getData().forEachIndexed { index, weather ->
                if(index<=23){
                    list.add(Entry(index.toFloat(), weather.tempC.toFloat()))
                }
            }
        } else{
            /*return nothing*/
        }
    return list
    }
}