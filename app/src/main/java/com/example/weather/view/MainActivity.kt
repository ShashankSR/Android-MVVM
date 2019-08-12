package com.example.weather.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
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
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.android.AndroidInjection
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SingleObserver<WeatherData>, LocationListener,
    GoogleApiClient.ConnectionCallbacks {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var weatherViewModel: WeatherViewModel

    lateinit var compositeDisposable: CompositeDisposable
    lateinit var loader: ImageView
    lateinit var forecastView: View
    lateinit var locationString: String
    private lateinit var animation: Animation
    private lateinit var slideInAnimation: Animation
    private lateinit var mGoogleApiClient: GoogleApiClient
    private val REQUEST_LOCATION = 99


    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this).build()
        mGoogleApiClient.connect()

        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {

            val forecastAdapter = SimpleRecyclerViewAdapter()

            layoutMain.rvForecast.apply {
                adapter = forecastAdapter
                addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            }
            layoutError.tvRetry.setOnClickListener {
                onRetryClicked()
            }
            weatherViewModel = this@MainActivity.weatherViewModel.apply {
                forecastVO.observe(this@MainActivity, Observer {
                    forecastAdapter.updateData(it)
                })
            }
            lifecycleOwner = this@MainActivity
            loader = imgLoader
            forecastView = layoutMain.cardView
        }

        animation = AnimationUtils.loadAnimation(baseContext, R.anim.rotation_infinite)
        slideInAnimation = AnimationUtils.loadAnimation(baseContext, R.anim.abc_slide_in_bottom)


    }

    override fun onSuccess(data: WeatherData) {
        loader.clearAnimation()
        weatherViewModel.onSuccess(data)
        forecastView.startAnimation(slideInAnimation)
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
        if (this::compositeDisposable.isInitialized) {
            compositeDisposable.dispose()
        }
        super.onStop()
    }


    private fun getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            val locationRequest = LocationRequest.create()
            locationRequest.interval = 500.toLong()
            locationRequest.fastestInterval = 100.toLong()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this)
        }
    }

    private fun onRetryClicked() {
        if (this::locationString.isInitialized) {
            weatherViewModel.getForecast(locationString).subscribe(this)
        } else {
            getDeviceLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getDeviceLocation()
                } else {
                    weatherViewModel.onError()
                }
            }
        }
    }


    override fun onConnected(p0: Bundle?) {
        getDeviceLocation()
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onLocationChanged(location: Location) {
        locationString = "${location.latitude},${location.longitude}"
        weatherViewModel.getForecast(locationString).subscribe(this@MainActivity)
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
    }

}
