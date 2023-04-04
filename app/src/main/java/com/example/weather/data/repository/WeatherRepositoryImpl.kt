package com.example.weather.data.repository

import android.util.Log
import com.example.weather.data.model.Weather
import com.example.weather.data.repository.dataSource.LocalDataSource
import com.example.weather.data.repository.dataSource.RemoteDatasource
import com.example.weather.data.room.WeatherEntity
import com.example.weather.domain.repository.WeatherRepository
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import java.lang.Exception

class WeatherRepositoryImpl(
    val localDataSource: LocalDataSource,
    val remoteDatasource: RemoteDatasource,
):WeatherRepository {
    override suspend fun getData(): List<WeatherEntity> {
        // function get data: to be implemented
        var weatherList = localDataSource.getdataFromdb()
        if (weatherList!=null){
            val remoteData = getDataFromRemote()
            // save data in local db
            saveDateInLocalDataSource(remoteData)
            weatherList = localDataSource.getdataFromdb()
        }
        return weatherList
    }

    override suspend fun updateData(): List<WeatherEntity> {
        //delete all data in local database
        deleteAllData()
        //get data from remote: create function
        val weatherList = getDataFromRemote()
        //save data in local data source: create
        saveDateInLocalDataSource(weatherList)
        //return
        return localDataSource.getdataFromdb()
    }

    /**get data from remote
     * @return List of WeatherEntity for each hour**/
    suspend fun getDataFromRemote():List<WeatherEntity>{
        lateinit var weatherResponse:Response<Weather>
        var weatherList:List<WeatherEntity> = emptyList()
        try {
            weatherResponse = remoteDatasource.getDataFromRemote()
            if(weatherResponse.body()!=null){
                weatherList = populateWeatherList(weatherResponse)
                Log.i("WEATHER", "data fetched and populated")
            }else{
                Log.i("WEATHER", "list is empty")
            }
        }catch (exception: Exception){
            Log.i("WEATHER", exception.message.toString())
        }
        return weatherList
    }
    /**Populate data
     * @param  Weather model class
     * @return List<WeatherEntity>**/
    suspend fun populateWeatherList(weatherForcast:Response<Weather>):List<WeatherEntity>{
        var weatherList:MutableList<WeatherEntity> = mutableListOf()
        lateinit var weather:WeatherEntity
        weatherForcast.body()?.forecast?.forecastday?.get(0)?.hour?.forEach { hour ->
            val weather = WeatherEntity(
                // id
                id=0,
                // cloud
                cloud=hour.cloud,
                // conditionIcon:
                conditionIcon =hour.condition.icon,
                // conditionText:
                conditionText=hour.condition.text,
                // humidity
                humidity=hour.humidity,
                // isDay
                isDay=hour.isDay,
                // precipMm
                precipMm=hour.precipMm,
                // tempC
                tempC=hour.tempC,
                // time
                time=hour.time,
                // timeEpoch
                timeEpoch=hour.timeEpoch,
                // uv
                uv=hour.uv,
                // willItRain
                willItRain=hour.willItRain,
                // willItSnow
                willItSnow=hour.willItSnow,
                // windKph: Double
                windKph=hour.windKph
                )
            weatherList.add(weather)
        }
        return weatherList
    }
    /**Save data in local data source
     * @param List of WeatherEntity **/
    suspend fun saveDateInLocalDataSource(weatherList:List<WeatherEntity>){
        localDataSource.savedataTodb(weatherList)
    }
    /**delete data in local data source **/
    suspend fun deleteAllData(){
        localDataSource.DeleteAll()
    }
}
