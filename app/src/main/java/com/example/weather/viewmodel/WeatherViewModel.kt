package com.example.weather.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.WeatherData
import com.example.weather.data.WeatherRepository
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeatherViewModel @Inject constructor(val repository: WeatherRepository) : ViewModel() {

    data class CurrentViewObject(
        val currentTemp: String,
        val currentLocation: String
    )

    data class ForecastViewObject(
        val temp: String,
        val day: String
    )

    data class NetworkViewObject(
        val showLoader: Int,
        val showError: Int,
        val showSuccess: Int
    )

    private val _networkVO = MutableLiveData<NetworkViewObject>().apply {
        value = NetworkViewObject(View.GONE, View.GONE, View.GONE)
    }

    private val _currentVO = MutableLiveData<CurrentViewObject>()
    private val _forecastVO = MutableLiveData<List<ForecastViewObject>>()

    val networkVO: LiveData<NetworkViewObject> = _networkVO
    var currentVO: LiveData<CurrentViewObject> = _currentVO
    val forecastVO: LiveData<List<ForecastViewObject>> = _forecastVO

    fun getForecast(location: String): Single<WeatherData> {
        _networkVO.postValue(NetworkViewObject(View.VISIBLE, View.GONE, View.GONE))
        return repository.getForecast(location)
    }

    fun onSuccess(weatherData: WeatherData) {
        _currentVO.postValue(
            CurrentViewObject(
                "${weatherData.current.temperature}\u00B0", weatherData.location.name
            )
        )
        _forecastVO.postValue(
            weatherData.forecast.forecastDay.map {
                ForecastViewObject("${it.day.avgTemperature} c", getDayFromDate(it.date))
            }
        )
        _networkVO.postValue(NetworkViewObject(View.GONE, View.GONE, View.VISIBLE))

    }

    private fun getDayFromDate(dateString: String): String {
        return try {
            SimpleDateFormat("EEEE", Locale.US).format(
                SimpleDateFormat("YYYY-MM-DD", Locale.US).parse(dateString)!!
            )
        } catch (e: NullPointerException) {
            dateString
        }
    }
}