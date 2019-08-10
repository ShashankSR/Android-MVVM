package com.example.weather.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.viewmodel.WeatherViewModel

class SimpleRecyclerViewAdapter : RecyclerView.Adapter<ForecastViewHolder>() {

    lateinit var data: List<WeatherViewModel.ForecastViewObject>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder =
        ForecastViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_forecast,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = if (this::data.isInitialized) data.size else 0


    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    fun updateData(forecastData: List<WeatherViewModel.ForecastViewObject>) {
        data = forecastData
        notifyDataSetChanged()
    }
}