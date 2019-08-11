package com.example.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.data.WeatherRepository
import com.example.weather.viewmodel.WeatherViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @Mock
    lateinit var weatherRepository: WeatherRepository
    lateinit var objectUnderTest: WeatherViewModel

    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun beforeEachTests() {
        objectUnderTest = WeatherViewModel(weatherRepository)
    }

    @Test
    fun testOnSuccess() {

    }

    @Test
    fun testOnError() {

    }

}