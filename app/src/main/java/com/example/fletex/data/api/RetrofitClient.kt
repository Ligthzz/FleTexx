package com.example.fletex.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // ------------------- BACKEND REMOTO -------------------
    private const val BASE_URL = "http://54.162.145.158:3000/api/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // ------------------- WEATHER API -------------------
    private const val WEATHER_URL = "https://api.openweathermap.org/data/2.5/"

    val weatherApi: WeatherAPI by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }
}
