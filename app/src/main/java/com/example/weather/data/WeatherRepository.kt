package com.example.weather.data

import javax.inject.Inject

class WeatherRepository @Inject constructor(val weatherApi: WeatherApi)