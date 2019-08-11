package com.example.weather

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.data.WeatherData
import com.example.weather.data.WeatherRepository
import com.example.weather.viewmodel.WeatherViewModel
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.nio.charset.Charset

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @MockK
    lateinit var weatherRepository: WeatherRepository
    lateinit var objectUnderTest: WeatherViewModel

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun beforeEachTests() {
        MockKAnnotations.init(this) // turn relaxUnitFun on for all mocks
        objectUnderTest = WeatherViewModel(weatherRepository)
    }

    @Test
    fun testOnSuccess() {

        var updateTimes = 0
        objectUnderTest.networkVO.observeForever {
            if (updateTimes == 0) {
                assert(it.showError == View.GONE)
                assert(it.showLoader == View.GONE)
                assert(it.showSuccess == View.GONE)
                updateTimes += 1
            } else {
                assert(it.showError == View.GONE)
                assert(it.showLoader == View.GONE)
                assert(it.showSuccess == View.VISIBLE)
            }
        }

        objectUnderTest.currentVO.observeForever {
            assert(it.currentTemp == "17°")
            assert(it.currentLocation == "Paris")
        }

        objectUnderTest.forecastVO.observeForever {
            assert(it.size == 4)
            assert(it[0] == WeatherViewModel.ForecastViewObject("18 C", "Monday"))
            assert(it[1] == WeatherViewModel.ForecastViewObject("19 C", "Tuesday"))
            assert(it[2] == WeatherViewModel.ForecastViewObject("20 C", "Wednesday"))
            assert(it[3] == WeatherViewModel.ForecastViewObject("21 C", "Thursday"))
        }


        objectUnderTest.onSuccess(loadData()!!)
    }

    @Test
    fun testOnError() {
        var updateTimes = 0
        objectUnderTest.networkVO.observeForever {
            if (updateTimes == 0) {
                assert(it.showError == View.GONE)
                assert(it.showLoader == View.GONE)
                assert(it.showSuccess == View.GONE)
                updateTimes += 1
            } else {
                assert(it.showError == View.VISIBLE)
                assert(it.showLoader == View.GONE)
                assert(it.showSuccess == View.GONE)
            }
        }
        objectUnderTest.onError()
    }

    private fun loadData(): WeatherData? {
        return try {
            val path = WeatherViewModelTest::class.java.getResource("/api-response/forecast.json")!!.path
            val br = BufferedReader(InputStreamReader(FileInputStream(path), Charset.defaultCharset()))
            Gson().fromJson<WeatherData>(br, WeatherData::class.java)
        } catch (ignore: FileNotFoundException) {
            null
        }
    }
}