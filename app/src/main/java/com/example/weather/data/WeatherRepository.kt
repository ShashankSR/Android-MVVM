package com.example.weather.data

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

    fun getForecast(location: String) = weatherApi.getForecast(location = location)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

}