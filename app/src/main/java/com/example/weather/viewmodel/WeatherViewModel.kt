package com.example.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {

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