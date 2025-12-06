package com.example.fletex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fletex.data.local.WeatherRepository
import com.example.fletex.data.model.WeatherResponse
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    var weather = mutableStateOf<WeatherResponse?>(null)
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf("")

    fun loadWeather(city: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                error.value = ""

                val data = repository.getWeather(city)
                weather.value = data

            } catch (e: Exception) {
                error.value = "No se pudo cargar el clima"
            } finally {
                isLoading.value = false
            }
        }
    }
}
