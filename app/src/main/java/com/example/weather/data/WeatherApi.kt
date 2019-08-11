package com.example.weather.data

import com.example.weather.BuildConfig
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/forecast.json")
    fun getForecast(
        @Query("key") string: String = BuildConfig.API_KEY,
        @Query("q") location: String = "Bengaluru",
        @Query("days") days: Int = 5
    ): Single<WeatherData>
}
