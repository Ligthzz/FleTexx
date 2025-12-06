package com.example.fletex.data.model

data class WeatherResponse(
    val name: String,
    val weather: List<WeatherDescription>,
    val main: WeatherMain
)

data class WeatherDescription(
    val description: String
)

data class WeatherMain(
    val temp: Double
)
