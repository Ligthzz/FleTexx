package com.example.fletex.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fletex.data.local.UserRepository
import com.example.fletex.data.model.User
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.delay

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    var isLoading = mutableStateOf(false)


    private val repository = UserRepository(application.applicationContext)

    var fullName = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var errorMessage = mutableStateOf("")

    //  Método para iniciar sesión con validaciones + Room
    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            val user = repository.loginUser(email.value, password.value)
            delay(600) // (opcional) para mostrar el spinner un instante
            if (user != null) {
                fullName.value = user.fullName
                errorMessage.value = ""
                isLoading.value = false
                onSuccess()
            } else {
                errorMessage.value = "Credenciales incorrectas"
                isLoading.value = false
            }
        }
    }


    //  Método de registro con validaciones campo a campo + guardado en Room
    fun register(
        fullName: String,
        phone: String,
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        when {
            fullName.isBlank() -> errorMessage.value = "El nombre completo es obligatorio"
            phone.isBlank() -> errorMessage.value = "El número de teléfono es obligatorio"
            !phone.matches(Regex("^\\+?\\d{8,15}\$")) -> errorMessage.value = "El teléfono no es válido"
            email.isBlank() -> errorMessage.value = "El correo electrónico es obligatorio"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> errorMessage.value = "El formato del correo no es válido"
            password.length < 6 -> errorMessage.value = "La contraseña debe tener al menos 6 caracteres"
            password != confirmPassword -> errorMessage.value = "Las contraseñas no coinciden"

            else -> {
                //  Si pasa todas las validaciones, guarda en SQLite local
                viewModelScope.launch {
                    val user = User(
                        fullName = fullName,
                        phone = phone,
                        email = email,
                        password = password
                    )
                    repository.registerUser(user)
                    errorMessage.value = ""
                    onSuccess()
                }
            }
        }
    }
}
