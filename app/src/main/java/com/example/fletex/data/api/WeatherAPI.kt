package com.example.fletex.data.api

import com.example.fletex.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("weather?units=metric&lang=es")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = "244f5e719a17118a25269f965d7f1c4f"
    ): WeatherResponse
}
