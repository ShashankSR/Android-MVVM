package com.example.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.WeatherRepository
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

    lateinit var networkVO: LiveData<NetworkViewObject>
    lateinit var currentVO: LiveData<CurrentViewObject>
    lateinit var forecastVO: LiveData<List<ForecastViewObject>>

}