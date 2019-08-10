package com.example.weather.data

import com.example.weather.BuildConfig
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    fun getForecast(
        @Query("key") string: String = BuildConfig.API_KEY,
        @Query("q") location: String = "Banglore",
        @Query("days") days: Int = 4
    ): Single<WeatherData>
}
