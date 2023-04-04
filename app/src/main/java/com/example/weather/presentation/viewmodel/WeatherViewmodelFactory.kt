package com.example.weather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.domain.useCases.*

class WeatherViewmodelFactory(
    private val getActualConditionIconUsesCases: GetActualConditionIconUsesCases,
    private val getActualTempUsesCase: GetActualTempUsesCase,
    private val getHourlyWeatherUsesCases: GetHourlyWeatherUsesCases,
    private val getHumidityUseCaseprivate: GetHumidityUseCaseprivate,
    private val getMoyenTempUseCases: GetMoyenTempUseCases,
    private val getWindVelocityUseCases: GetWindVelocityUseCases,
    private val updateForecastDataUsesCase: UpdateForecastDataUsesCase,
    private val getDataForPlotChart: GetDataForPlotChart,
    private val getPeakTempUsesCases: GetPeakTempUsesCases) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewmodel(
            getActualConditionIconUsesCases,
            getActualTempUsesCase,
            getHourlyWeatherUsesCases,
            getHumidityUseCaseprivate,
            getMoyenTempUseCases,
            getWindVelocityUseCases,
            updateForecastDataUsesCase,
            getDataForPlotChart,
            getPeakTempUsesCases) as T
    }
}