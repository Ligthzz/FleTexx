package com.example.fletex.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fletex.data.model.User

class AuthViewModel : ViewModel() {

    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var errorMessage = mutableStateOf("")

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
            onSuccess()
        } else {
            errorMessage.value = "Credenciales incorrectas"
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
                errorMessage.value = ""
                onSuccess()
            }
        }
    }
}
