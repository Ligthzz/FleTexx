package com.example.fletex.ui.viewmodel

import android.app.Application
import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        // Contexto REAL de Android (emulador o dispositivo)
        val context = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as Application

        viewModel = MainViewModel(context)
    }

    @Test
    fun setPhotoUri_actualiza_el_stateflow() {
        val uri = Uri.parse("content://test/photo.jpg")

        viewModel.setPhotoUri(uri)

        assertEquals(uri, viewModel.photoUri.value)
    }
}
