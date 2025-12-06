package com.example.fletex.data.local

import com.example.fletex.data.api.RetrofitClient
import com.example.fletex.data.model.WeatherResponse

class WeatherRepository {

    suspend fun getWeather(city: String): WeatherResponse {
        return RetrofitClient.weatherApi.getWeather(city)
    }
}
