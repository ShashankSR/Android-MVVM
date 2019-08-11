package com.example.weather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weather.R
import com.example.weather.data.WeatherData
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.utils.ViewModelFactory
import com.example.weather.viewmodel.WeatherViewModel
import dagger.android.AndroidInjection
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SingleObserver<WeatherData> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var weatherViewModel: WeatherViewModel

    lateinit var compositeDisposable: CompositeDisposable

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
            layoutError.tvRetry.setOnClickListener {
                this@MainActivity.weatherViewModel.getForecast("").subscribe(this@MainActivity)
            }
            weatherViewModel = this@MainActivity.weatherViewModel.apply {
                forecastVO.observe(this@MainActivity, Observer {
                    forecastAdapter.updateData(it)
                })
            }
        }

        weatherViewModel.getForecast("").subscribe(this)
    }

    override fun onSuccess(data: WeatherData) {
        weatherViewModel.onSuccess(data)
    }

    override fun onSubscribe(d: Disposable) {
        if (!this@MainActivity::compositeDisposable.isInitialized) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        weatherViewModel.onError()
    }

    override fun onStop() {
        compositeDisposable.dispose()
        super.onStop()
    }
}
