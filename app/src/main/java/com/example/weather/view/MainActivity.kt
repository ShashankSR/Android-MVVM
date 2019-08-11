package com.example.weather.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weather.R
import com.example.weather.data.WeatherData
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.utils.ViewModelFactory
import com.example.weather.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices
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
    lateinit var loader: ImageView
    lateinit var locationString: String
    private lateinit var animation: Animation

    private val REQUEST_LOCATION = 99

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
                this@MainActivity.weatherViewModel.getForecast(locationString).subscribe(this@MainActivity)
            }
            weatherViewModel = this@MainActivity.weatherViewModel.apply {
                forecastVO.observe(this@MainActivity, Observer {
                    forecastAdapter.updateData(it)
                })
            }
            lifecycleOwner = this@MainActivity
            loader = imgLoader
            animation = AnimationUtils.loadAnimation(baseContext, R.anim.rotation_infinite)
        }

        if (checkLocationPermission()) {
            LocationServices.getFusedLocationProviderClient(this).apply {
                lastLocation.addOnSuccessListener { location ->
                    locationString = "${location.latitude},${location.longitude}"
                    weatherViewModel.getForecast(locationString).subscribe(this@MainActivity)
                }
            }
        }

    }

    override fun onSuccess(data: WeatherData) {
        loader.clearAnimation()
        weatherViewModel.onSuccess(data)
    }

    override fun onSubscribe(d: Disposable) {
        loader.startAnimation(animation)
        if (!this@MainActivity::compositeDisposable.isInitialized) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        loader.clearAnimation()
        weatherViewModel.onError()
    }

    override fun onStop() {
        compositeDisposable.dispose()
        super.onStop()
    }


    private fun checkLocationPermission(): Boolean {
        return if (
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_LOCATION
            )
            false
        } else {
            true
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {

                        LocationServices.getFusedLocationProviderClient(this).apply {
                            lastLocation.addOnSuccessListener { location ->
                                locationString = "${location.latitude},${location.longitude}"
                                weatherViewModel.getForecast(locationString).subscribe(this@MainActivity)
                            }
                        }
                    }
                } else {
                    weatherViewModel.onError()
                }
            }
        }
    }
}
