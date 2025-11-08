package com.example.fletex.ui.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.net.Uri
import android.os.Looper
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.fletex.data.model.User
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class AuthViewModel : ViewModel() {

    var fullName = mutableStateOf("")

    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var errorMessage = mutableStateOf("")

    fun onFullNameChange(newName: String) {
        fullName.value = newName
    }

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun login(onSuccess: () -> Unit) {
        if (email.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = "Por favor completa todos los campos"
        } else if (email.value == "admin@gmail.com" && password.value == "1234") {
            errorMessage.value = ""
            //login de prueba
            fullName.value = "Administrador"
            errorMessage.value = ""
            onSuccess()
        } else {
            //validar despues al conectar con firebase
            errorMessage.value = "contraseña o usuario incorrecto"
        }
    }

    fun register(
        fullName: String,
        phone: String,
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        when {
            fullName.isBlank() || phone.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                errorMessage.value = "Completa todos los campos"
            }

            password != confirmPassword -> {
                errorMessage.value = "Las contraseñas no coinciden"
            }

            else -> {
                // Aquí podrías guardar el usuario en BD o Firebase
                this.fullName.value = fullName //  guardamos el nombre para el Home
                this.email.value = email
                errorMessage.value = ""
                onSuccess()
            }
        }
    }

}
