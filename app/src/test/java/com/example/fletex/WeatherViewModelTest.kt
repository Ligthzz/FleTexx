package com.example.fletex

import com.example.fletex.ui.viewmodel.WeatherViewModel



import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        viewModel = WeatherViewModel()
    }

    // ----------------------------------------------------
    //              ESTADO INICIAL
    // ----------------------------------------------------

    @Test
    fun estado_inicial_es_correcto() {
        assertNull(viewModel.weather.value)
        assertFalse(viewModel.isLoading.value)
        assertEquals("", viewModel.error.value)
    }

    // ----------------------------------------------------
    //              CAMBIO DE ESTADO LOCAL
    // ----------------------------------------------------

    @Test
    fun error_puede_ser_asignado_localmente() {
        viewModel.error.value = "Error de prueba"
        assertEquals("Error de prueba", viewModel.error.value)
    }

    @Test
    fun loading_puede_cambiar_de_estado() {
        viewModel.isLoading.value = true
        assertTrue(viewModel.isLoading.value)
    }
}
