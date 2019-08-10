package com.example.weather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weather.R
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.utils.ViewModelFactory
import com.example.weather.viewmodel.WeatherViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {

            val forecastAdapter = SimpleRecyclerViewAdapter()

            layoutMain.rvForecast.apply {
                adapter = forecastAdapter
                addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            }

            weatherViewModel = this@MainActivity.weatherViewModel.apply {
                forecastVO.observe(this@MainActivity, Observer {
                    forecastAdapter.updateData(it)
                })
            }
        }
    }

}
