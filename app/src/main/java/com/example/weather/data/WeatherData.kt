package com.example.weather.data

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
) {

    data class Location(
        @SerializedName("lat")
        val latitude: Double,
        @SerializedName("localtime")
        val localtime: String,
        @SerializedName("lon")
        val longitude: Double,
        @SerializedName("name")
        val name: String,
        @SerializedName("tz_id")
        val tzId: String
    )


    data class Forecast(
        @SerializedName("forecastday")
        val forecastDay: List<Forecastday>
    ) {

        data class Forecastday(
            @SerializedName("date")
            val date: String,
            @SerializedName("day")
            val day: Day
        )
    }

    data class Current(
        @SerializedName("is_day")
        val isDay: Int,
        @SerializedName("last_updated")
        val lastUpdated: String,
        @SerializedName("temp_c")
        val temperature: Double
    )

    data class Day(
        @SerializedName("avgtemp_c")
        val avgTemperature: Double
    )
}