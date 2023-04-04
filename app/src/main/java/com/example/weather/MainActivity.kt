package com.example.weather

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.weather.alarmManager.Alarm
import com.example.weather.data.api.RetrofitInstance
import com.example.weather.data.api.WeatherServices
import com.example.weather.data.repository.WeatherRepositoryImpl
import com.example.weather.data.repository.dataSource.dataSourceImpl.LocalDataSourceImpl
import com.example.weather.data.repository.dataSource.dataSourceImpl.RemoteDataSourceImpl
import com.example.weather.data.room.WeatherDAO
import com.example.weather.data.room.WeatherDatabase
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.useCases.*
import com.example.weather.presentation.alarmManager.Receiver
import com.example.weather.presentation.recyclerView.RecyclerWeatherAdapter
import com.example.weather.presentation.viewmodel.WeatherViewmodel
import com.example.weather.presentation.viewmodel.WeatherViewmodelFactory
import com.example.weather.presentation.workmanager.NotifHourlyWeatherForcast
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    //viewmodel
    lateinit var factory: WeatherViewmodelFactory
    lateinit var weatherViewmodel: WeatherViewmodel
    //Uses Cases
    lateinit var getActualConditionIconUsesCases: GetActualConditionIconUsesCases
    lateinit var getActualTempUsesCase: GetActualTempUsesCase
    lateinit var getHourlyWeatherUsesCases: GetHourlyWeatherUsesCases
    lateinit var getHumidityUseCaseprivate: GetHumidityUseCaseprivate
    lateinit var getMoyenTempUseCases: GetMoyenTempUseCases
    lateinit var getWindVelocityUseCases: GetWindVelocityUseCases
    lateinit var updateForecastDataUsesCase: UpdateForecastDataUsesCase
    lateinit var getDataForPlotChart: GetDataForPlotChart
    lateinit var getPeakTempUsesCases: GetPeakTempUsesCases

    //Repository
    lateinit var weatherRepository: WeatherRepository
    //Database
    lateinit var weatherDb:WeatherDatabase
    //dao
    lateinit var weatherDAO: WeatherDAO
    //recyclerview
    lateinit var recyclerView: RecyclerView
    //notification
    private val channelID= "com.example.weather.channel1"
    private var notificationManager: NotificationManager?= null
    // alarm
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    // meanTemp Data to alarm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val retService=RetrofitInstance.getRetrofitInstance().create(WeatherServices::class.java)

        /**database instatiation***/
        weatherDb = WeatherDatabase.getInstance(this)
        /**dao instatiation***/
        weatherDAO = weatherDb.weatherDao

        /**dataSource instatiation***/
        val localDataSource = LocalDataSourceImpl(weatherDAO)
        val remoteDataSource = RemoteDataSourceImpl(retService)
        /**repository instatiation***/
        weatherRepository = WeatherRepositoryImpl(localDataSource,remoteDataSource)
        /**useCases instantiation**/
        getActualConditionIconUsesCases= GetActualConditionIconUsesCases(weatherRepository)
        getActualTempUsesCase=GetActualTempUsesCase(weatherRepository)
        getHourlyWeatherUsesCases=GetHourlyWeatherUsesCases(weatherRepository)
        getHumidityUseCaseprivate=GetHumidityUseCaseprivate(weatherRepository)
        getMoyenTempUseCases= GetMoyenTempUseCases(weatherRepository)
        getWindVelocityUseCases= GetWindVelocityUseCases(weatherRepository)
        updateForecastDataUsesCase= UpdateForecastDataUsesCase(weatherRepository)
        getDataForPlotChart= GetDataForPlotChart(weatherRepository)
        getPeakTempUsesCases= GetPeakTempUsesCases(weatherRepository)
        /**Viewmodel instantiation***/
        factory = WeatherViewmodelFactory(
            getActualConditionIconUsesCases,
            getActualTempUsesCase,
            getHourlyWeatherUsesCases,
            getHumidityUseCaseprivate,
            getMoyenTempUseCases,
            getWindVelocityUseCases,
            updateForecastDataUsesCase,
            getDataForPlotChart,
            getPeakTempUsesCases)

        weatherViewmodel= ViewModelProvider(this,factory)
            .get(WeatherViewmodel::class.java)

        /** databinding **/
        binding.weathermodel =weatherViewmodel

        //val response = weatherViewmodel.getForcastWeather()
        val response = weatherViewmodel.updateForcastWeather()

        response.observe(this, Observer {
            //Log.i("ICON" ,"the condition icon is ${it.get(0).tempC}")
            Log.i("TEMP" ,"the temperature is ${it.get(0).tempC}")
            Log.i("RESPONSESIZE" ,"the size of the response ${it.size}")
        })

        /*display the peak temperature of the day*/
        val peakTemp = weatherViewmodel.peakTempOfTheDay()
        peakTemp.observe(this, Observer {
            binding.tempGeneralJour.text = String.format("%.1f", it)
        })

        /*display the mean temperature of the day*/
        val meanTemp= weatherViewmodel.meanTempOfTheDay()
        meanTemp.observe(this, Observer {
            binding.TemperatureTextView.text = String.format("%.1f", it)
        })

        /*display the humidity temperature of the day*/
        val humidity = weatherViewmodel.actualHumidity()
        humidity.observe(this, Observer {
            binding.humidityGeneralJour.text = String.format("%.1f", it)
        })
        /*display the wind velocity of the day*/
        val wind = weatherViewmodel.meanWindVelocity()
        wind.observe(this, Observer {
            binding.windVelocity.text = String.format("%.1f", it)
        })


        /**FAB button onclick management**/
        binding.fabWeatherList.setOnClickListener {
           /* scheduleNotifications(this)
            Toast.makeText(this,"button clicked",Toast.LENGTH_SHORT).show()*/
            val data = weatherViewmodel.weatherForecast.value
            val intent =Intent(this,ListedWeatherData::class.java)
            intent.putExtra("weather",data as java.io.Serializable)
            startActivity(intent)
        }
        /**chart implementation**/
        val entries = weatherViewmodel.plotDataSet()
        entries.observe(this, Observer {
            val dataSet = LineDataSet(it, "")
            dataSet.color = Color.RED
            dataSet.valueTextColor = Color.WHITE
            dataSet.setCircleColor(Color.WHITE)
            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            dataSet.setDrawFilled(true)
            dataSet.fillAlpha =200
            dataSet.fillColor = resources.getColor(R.color.yellowSun)
            dataSet.disableDashedLine()
            dataSet.setDrawHighlightIndicators(false)
            val lineData = LineData(dataSet)
            binding.TemperatureChart.axisRight.isEnabled = false
            binding.TemperatureChart.axisLeft.setDrawGridLines(false)
            binding.TemperatureChart.setDrawGridBackground(false)
            binding.TemperatureChart.highlightValue(null)
            binding.TemperatureChart.axisLeft.textSize = 10f
            binding.TemperatureChart.xAxis.textSize =10f
            binding.TemperatureChart.legend.isEnabled = false
            binding.TemperatureChart.axisLeft.textColor = Color.WHITE
            binding.TemperatureChart.xAxis.textColor = Color.WHITE
            binding.TemperatureChart.description.isEnabled = false
            binding.TemperatureChart.animateY(600,Easing.EaseOutCubic)
            binding.TemperatureChart.data = lineData
            binding.TemperatureChart.invalidate()
        })


        /**recyclerview**/
        recyclerView=findViewById(R.id.HourlyRecyclerViewWeather)
        val lineareLayoutManager =LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        recyclerView.layoutManager = lineareLayoutManager

        //val weatherForcast = weatherViewmodel.getForcastWeather()
        val forcast = weatherViewmodel.weatherForecast
        forcast.observe(this, Observer {
            recyclerView.adapter = RecyclerWeatherAdapter(it)
        })
        /**workmanager execution**/

        /**Alarm Manager**/
        alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, Receiver::class.java).let { intent ->
            PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
        }
        val meanintent:Intent = Intent("meanTemp")
        meanintent.putExtra("extra",meanTemp.value)
        sendBroadcast(intent)
        // Set the alarm to start at 6h.
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 6)
            set(Calendar.MINUTE, 0)
        }
        // setRepeating() lets you specify a precise custom interval--in this case,
        // 6 hour.
        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            1000 * 60 * 6,
            alarmIntent
        )
    }


    /**workmanager**/
    fun scheduleNotifications(context: Context) {
        // Create the work request
        val notificationWeather = PeriodicWorkRequest.Builder(NotifHourlyWeatherForcast::class.java,1,
            java.util.concurrent.TimeUnit.HOURS,
            10,
            java.util.concurrent.TimeUnit.MINUTES)
            .build()

        // Schedule the work request
       /* WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "hourly_notification",
            ExistingPeriodicWorkPolicy.REPLACE,
            notificationWorkRequest
        )*/
    WorkManager.getInstance(applicationContext)
        .enqueue(notificationWeather)
    }

}