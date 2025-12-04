package com.example.fletex.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fletex.data.local.UserRepository
import com.example.fletex.data.model.User
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import com.example.fletex.data.model.Vehicle
import kotlinx.coroutines.delay


class AuthViewModel(application: Application) : AndroidViewModel(application) {

    var isLoading = mutableStateOf(false)

    private val repository = UserRepository(application.applicationContext)

    var fullName = mutableStateOf("")
    var phone = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var errorMessage = mutableStateOf("")
    var userId = mutableStateOf<Int?>(null)
    var role = mutableStateOf("usuario") // <- vivo

    fun register(
        fullName: String,
        phone: String,
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        // VALIDACIONES
        when {
            fullName.isBlank() -> {
                errorMessage.value = "El nombre es obligatorio"
                return
            }

            phone.isBlank() || !phone.matches(Regex("^\\+?\\d{8,15}$")) -> {
                errorMessage.value = "Número de teléfono inválido"
                return
            }

            email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                errorMessage.value = "Correo inválido"
                return
            }

            password.length < 6 -> {
                errorMessage.value = "La contraseña debe tener mínimo 6 caracteres"
                return
            }

            password != confirmPassword -> {
                errorMessage.value = "Las contraseñas no coinciden"
                return
            }
        }

        // SI ESTA TODO BIEN
        viewModelScope.launch {
            val newUser = User(
                fullName = fullName,
                phone = phone,
                email = email,
                password = password,
                role = "usuario"   //  IMPORTANTE POR EL CAMBIO DE ROL
            )

            repository.registerUser(newUser)

            // limpia estados
            this@AuthViewModel.fullName.value = ""
            this@AuthViewModel.email.value = ""
            this@AuthViewModel.password.value = ""

            errorMessage.value = ""

            onSuccess()
        }
    }


    // LOGIN (actualizado para setear userId y role)
    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            val user = repository.loginUser(email.value, password.value)
            delay(600)
            if (user != null) {
                fullName.value = user.fullName
                phone.value = user.phone
                userId.value = user.id
                role.value = user.role
                email.value = user.email
                password.value = user.password
                errorMessage.value = ""
                isLoading.value = false
                onSuccess()
            } else {
                errorMessage.value = "Credenciales incorrectas"
                isLoading.value = false
            }
        }
    }
    fun deleteUser(
        id: Int,
        passwordEntered: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (passwordEntered.isBlank()) {
            onError("Debes ingresar tu contraseña")
            return
        }

        viewModelScope.launch {
            // 1. Obtener el usuario desde la BD
            val user = repository.getUserById(id)

            if (user == null) {
                onError("Error: Usuario no encontrado")
                return@launch
            }

            // 2. Validar contraseña
            if (user.password != passwordEntered) {
                onError("La contraseña es incorrecta")
                return@launch
            }

            // 3. Borrar vehículos asociados
            repository.deleteVehiclesByUser(id)

            // 4. Borrar al usuario
            repository.deleteUser(user.id)


            // 5. Limpiar estados del viewmodel
            fullName.value = ""
            email.value = ""
            password.value = ""
            userId.value = null
            role.value = "usuario"

            onSuccess()
        }
    }

    fun getUserName(): String {
        return fullName.value.ifBlank { "Usuario" }
    }
    fun getUserRole(): String {
        return role.value
    }


    fun updateUser(
        id: Int,
        fullName: String,
        phone: String,
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        // VALIDACIONES
        when {
            fullName.isBlank() -> {
                errorMessage.value = "El nombre no puede estar vacío"
                return
            }
            phone.isBlank() -> {
                errorMessage.value = "El teléfono es obligatorio"
                return
            }
            !phone.matches(Regex("^\\+?\\d{8,15}\$")) -> {
                errorMessage.value = "Formato de teléfono no válido"
                return
            }
            email.isBlank() -> {
                errorMessage.value = "El correo no puede estar vacío"
                return
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                errorMessage.value = "Formato de correo inválido"
                return
            }

            // si escribe contraseña nueva, debe confirmar
            password.isNotBlank() && password != confirmPassword -> {
                errorMessage.value = "Las contraseñas no coinciden"
                return
            }
        }

        viewModelScope.launch {
            val newPassword = if (password.isBlank()) {
                this@AuthViewModel.password.value // mantiene la original
            } else {
                password
            }

            val updatedUser = User(
                id = id,
                fullName = fullName,
                phone = phone,
                email = email,
                password = newPassword,
                role = role.value // mantiene su rol actual
            )

            repository.updateUser(updatedUser)
            errorMessage.value = ""

            // Actualiza datos en memoria también
            this@AuthViewModel.fullName.value = fullName
            this@AuthViewModel.email.value = email
            this@AuthViewModel.password.value = newPassword

            onSuccess()
        }
    }


    // Registrar vehículo -> cambia a "conductor"
    fun registerVehicle(
        tipo: String,
        patente: String,
        tamano: String,
        capacidad: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val uid = userId.value
        if (uid == null) { onError("Usuario no válido"); return }

        // validaciones simples
        when {
            tipo.isBlank() -> onError("El tipo de flete es obligatorio")
            patente.isBlank() -> onError("La patente es obligatoria")
            tamano.isBlank() -> onError("El tamaño es obligatorio")
            capacidad.isBlank() -> onError("La capacidad es obligatoria")
            else -> viewModelScope.launch {
                repository.insertVehicle(
                    Vehicle(
                        userId = uid,
                        tipo = tipo.trim(),
                        patente = patente.trim(),
                        tamano = tamano.trim(),
                        capacidad = capacidad.trim()
                    )
                )
                // actualiza rol en DB y en memoria
                repository.updateUserRole(uid, "conductor")
                role.value = "conductor"
                onSuccess()
            }
        }
    }

    // Eliminar TODOS los vehículos del usuario -> vuelve a "usuario"
    fun removeAllVehiclesAndDowngrade(onSuccess: () -> Unit) {
        val uid = userId.value ?: return
        viewModelScope.launch {
            repository.deleteVehiclesByUser(uid)
            repository.updateUserRole(uid, "usuario")
            role.value = "usuario"
            onSuccess()
        }
    }

    fun getMyVehicles(onResult: (List<Vehicle>) -> Unit) {
        val uid = userId.value ?: return

        viewModelScope.launch {
            val list = repository.getVehiclesByUser(uid)
            onResult(list)
        }
    }

    fun eliminarVehiculoConPassword(
        passwordIngresada: String,
        onError: (String) -> Unit,
        onSuccess: () -> Unit
    ) {
        val uid = userId.value ?: return onError("Usuario inválido")

        if (passwordIngresada.isBlank()) {
            onError("Debes ingresar tu contraseña")
            return
        }

        viewModelScope.launch {

            // Obtener usuario REAL
            val user = repository.getUserById(uid)

            if (user == null) {
                onError("Usuario no encontrado")
                return@launch
            }

            // Validar contraseña
            if (user.password != passwordIngresada) {
                onError("Contraseña incorrecta")
                return@launch
            }

            // Eliminar vehículos
            repository.deleteVehiclesByUser(uid)

            // Restaurar rol normal
            repository.updateUserRole(uid, "usuario")
            role.value = "usuario"

            onSuccess()
        }
    }
    fun updateVehicle(v: Vehicle) {
        viewModelScope.launch {
            repository.updateVehicle(v)
        }
    }






}
