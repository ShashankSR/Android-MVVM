package com.example.weather

import com.example.weather.data.WeatherData
import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.nio.charset.Charset

class MockServerDispatcher {

    inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(200).setBody(loadData())
        }
    }

    inner class ErrorDispatcher : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {

            return MockResponse().setResponseCode(400)

        }
    }


    private fun loadData(): String? {
        return try {
            val path = MockServerDispatcher::class.java.getResource("/api-response/forecast.json")!!.path
            val br = BufferedReader(InputStreamReader(FileInputStream(path), Charset.defaultCharset()))
            Gson().fromJson<WeatherData>(br, WeatherData::class.java).toString()
        } catch (ignore: FileNotFoundException) {
            null
        }
    }
}