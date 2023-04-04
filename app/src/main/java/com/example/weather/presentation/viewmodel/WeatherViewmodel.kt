package com.example.weather.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.weather.data.room.WeatherEntity
import com.example.weather.domain.useCases.*
import com.example.weather.presentation.utils.utils.Companion.formatter
import java.time.LocalDateTime

class WeatherViewmodel(
    private val getActualConditionIconUsesCases: GetActualConditionIconUsesCases,
    private val getActualTempUsesCase: GetActualTempUsesCase,
    private val getHourlyWeatherUsesCases: GetHourlyWeatherUsesCases,
    private val getHumidityUseCaseprivate: GetHumidityUseCaseprivate,
    private val getMoyenTempUseCases: GetMoyenTempUseCases,
    private val getWindVelocityUseCases: GetWindVelocityUseCases,
    private val updateForecastDataUsesCase: UpdateForecastDataUsesCase,
    private val getDataForPlotChart: GetDataForPlotChart,
    private val getPeakTempUsesCases: GetPeakTempUsesCases):ViewModel() {

    var weatherForecast:LiveData<List<WeatherEntity>>
    var weatherMeanTempOfTheDay:LiveData<Double>
 /*   var weatherHumidityOfTheDay:LiveData<String>
    var weatherMeanWindVelocityOfTheDay:LiveData<String>*/
    var currentDate:String

    init {
        weatherForecast = updateForcastWeather()
        weatherMeanTempOfTheDay = meanTempOfTheDay()
        //date = weatherForecast.value[0]. // add the date in entity
/*        weatherHumidityOfTheDay = actualHumidity().toString() as LiveData<String>
        weatherMeanWindVelocityOfTheDay = meanWindVelocity().toString() as LiveData<String>*/
        currentDate= LocalDateTime.now().format(formatter)
    }

    /** get weather forecast data from data base &/or API **/
    fun getForcastWeather() = liveData {
        val forecast = getHourlyWeatherUsesCases.execute()
        emit(forecast)
    }

    /** update weather forecast data from data base &/or API **/
    fun updateForcastWeather() = liveData {
        val forecast = updateForecastDataUsesCase.execute()
        emit(forecast)
    }

    /** get the actual icon to be used **/
    fun actualConditionIcon() = liveData {
        val icon = getActualConditionIconUsesCases.execute()
        emit(icon)
    }

    /** get the temperature of the hour of the day to be used **/
    fun actualTemperature() = liveData {
        val actualtemp = getActualTempUsesCase.execute()
        emit(actualtemp)
    }

    /** get the mean humidity of the day **/
    fun actualHumidity() = liveData {
        val humidity = getHumidityUseCaseprivate.execute()
        emit(humidity)
    }

    /** get the mean temperature of the day to be used **/
    fun meanTempOfTheDay() = liveData {
        val meantemp = getMoyenTempUseCases.execute()
        emit(meantemp)
    }

    /** get the mean of the  of the day to be used **/
    fun meanWindVelocity() = liveData {
        val wind = getWindVelocityUseCases.execute()
        emit(wind)
    }

    /**get temperature data to be plotted**/
    fun plotDataSet() = liveData {
        val dataList = getDataForPlotChart.execute()
        emit(dataList)
    }

    /**get temperature data to be plotted**/
    fun peakTempOfTheDay() = liveData {
        val data = getPeakTempUsesCases.execute()
        emit(data)
    }
}