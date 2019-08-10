package com.example.weather.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    private val day = item.tv_forecast_day
    private val temperature = item.tv_forecast_temp

    fun onBind(data: WeatherViewModel.ForecastViewObject) {
        day.text = data.day
        temperature.text = data.temp
    }
}