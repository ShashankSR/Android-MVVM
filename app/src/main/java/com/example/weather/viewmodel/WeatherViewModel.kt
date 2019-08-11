package com.example.weather.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.WeatherData
import com.example.weather.data.WeatherRepository
import io.reactivex.Single
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

    private var _networkVO = MutableLiveData<NetworkViewObject>().apply {
        value = NetworkViewObject(View.GONE, View.GONE, View.GONE)
    }

    val networkVO: LiveData<NetworkViewObject> = _networkVO
    lateinit var currentVO: LiveData<CurrentViewObject>
    lateinit var forecastVO: LiveData<List<ForecastViewObject>>

    fun getForecast(location: String): Single<WeatherData> {
        _networkVO.postValue(NetworkViewObject(View.VISIBLE, View.GONE, View.GONE))
        return repository.getForecast(location)
    }
}