package com.example.fletex.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fletex.data.model.UserRemote
import com.example.fletex.data.model.VehicleRemote
import com.example.fletex.data.repository.RemoteUserRepository
import com.example.fletex.data.repository.RemoteVehicleRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    // Repositorios
    private val userRepo = RemoteUserRepository()
    private val vehicleRepo = RemoteVehicleRepository()

    // Estado del usuario logeado
    var remoteUserId = mutableStateOf("")
    var fullName = mutableStateOf("")
    var phone = mutableStateOf("")
    var email = mutableStateOf("")
    var role = mutableStateOf("usuario")
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    var password = mutableStateOf("")


    // ----------------------------------------------------
    //                  REGISTER REMOTO
    // ----------------------------------------------------
    fun registerRemote(
        fullName: String,
        phone: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {

        try {
            isLoading.value = true

            val created = userRepo.registerUser(fullName, phone, email, password)

            remoteUserId.value = created._id ?: ""
            this@AuthViewModel.fullName.value = created.fullName
            this@AuthViewModel.phone.value = created.phone
            this@AuthViewModel.email.value = created.email
            this@AuthViewModel.role.value = created.role

            onSuccess()

        } catch (e: Exception) {
            onError(e.message ?: "Error registrando usuario")
        } finally {
            isLoading.value = false
        }
    }

    // ----------------------------------------------------
    //                     LOGIN
    // ----------------------------------------------------
    fun loginRemote(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {

        try {
            isLoading.value = true

            val response = userRepo.login(email, password)

            remoteUserId.value = response.user._id ?: ""
            fullName.value = response.user.fullName
            phone.value = response.user.phone
            this@AuthViewModel.email.value = response.user.email
            role.value = response.user.role

            onSuccess()

        } catch (e: Exception) {
            onError("Credenciales inválidas")
        } finally {
            isLoading.value = false
        }
    }


    // ----------------------------------------------------
    //               ACTUALIZAR PERFIL REMOTO
    // ----------------------------------------------------
    fun updateUserRemote(
        fullName: String,
        phone: String,
        email: String,
        newPassword: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {

        val id = remoteUserId.value
        if (id.isBlank()) {
            onError("Usuario inválido")
            return@launch
        }

        isLoading.value = true

        try {
            val body = UserRemote(
                _id = id,
                fullName = fullName,
                phone = phone,
                email = email,
                password = newPassword ?: "",
                role = role.value
            )

            val updated = userRepo.updateUser(id, body)

            this@AuthViewModel.fullName.value = updated.fullName
            this@AuthViewModel.phone.value = updated.phone
            this@AuthViewModel.email.value = updated.email
            this@AuthViewModel.role.value = updated.role

            onSuccess()

        } catch (e: retrofit2.HttpException) {
            if (e.code() == 409) {
                onError("Este correo ya está registrado")
            } else {
                onError("Error actualizando usuario")
            }
        } catch (e: Exception) {
            onError("Error actualizando usuario")
        } finally {
            isLoading.value = false
        }
    }



    // ----------------------------------------------------
    //               OBTENER MIS VEHÍCULOS
    // ----------------------------------------------------
    fun getMyVehiclesRemote(
        onResult: (List<VehicleRemote>) -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {

        val id = remoteUserId.value
        if (id.isBlank()) {
            onResult(emptyList())
            return@launch
        }

        try {
            val list = vehicleRepo.getMyVehicles(id)
            onResult(list)
        } catch (e: Exception) {
            onError(e.message ?: "Error obteniendo vehículos")
        }
    }


    // ----------------------------------------------------
    //               EDITAR VEHÍCULO REMOTO
    // ----------------------------------------------------
    fun updateVehicleRemote(
        vehicle: VehicleRemote,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {

        try {
            val id = vehicle._id ?: return@launch onError("Vehículo sin ID")

            vehicleRepo.updateVehicle(id, vehicle)
            onSuccess()

        } catch (e: Exception) {
            onError(e.message ?: "Error actualizando vehículo")
        }
    }

    // ----------------------------------------------------
    //               ELIMINAR VEHÍCULO REMOTO
    // ----------------------------------------------------
    fun deleteVehicleRemote(
        vehicleId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {

        try {
            vehicleRepo.deleteVehicle(vehicleId)
            onSuccess()

        } catch (e: Exception) {
            onError(e.message ?: "Error eliminando vehículo")
        }
    }


    // ----------------------------------------------------
    //     BAJAR ROL SI NO QUEDAN VEHÍCULOS
    // ----------------------------------------------------
    fun downgradeRoleIfNoVehicles() = viewModelScope.launch {

        val userId = remoteUserId.value
        if (userId.isBlank()) return@launch

        val list = vehicleRepo.getMyVehicles(userId)

        if (list.isEmpty()) {
            role.value = "usuario"

            userRepo.updateUser(
                userId,
                UserRemote(
                    _id = userId,
                    fullName = fullName.value,
                    phone = phone.value,
                    email = email.value,
                    password = "",
                    role = "usuario"
                )
            )
        }
    }

    // ---------------------------
//   ELIMINAR CUENTA REMOTO
// ---------------------------
    fun deleteUserRemote(
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {

        try {
            isLoading.value = true

            val id = remoteUserId.value
            if (id.isBlank()) {
                onError("Usuario inválido")
                return@launch
            }

            userRepo.deleteUserRemote(id, password)

            // Limpiar memoria
            remoteUserId.value = ""
            fullName.value = ""
            email.value = ""
            phone.value = ""
            role.value = "usuario"

            onSuccess()

        } catch (e: Exception) {
            onError(e.message ?: "Error eliminando usuario")
        } finally {
            isLoading.value = false
        }
    }

    // ----------------------------------------------------
//  ELIMINAR TODOS LOS VEHÍCULOS Y BAJAR ROL
// ----------------------------------------------------
    fun removeAllVehiclesAndDowngrade(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {

        val id = remoteUserId.value
        if (id.isBlank()) {
            onError("Usuario inválido")
            return@launch
        }

        try {
            // Obtener todos los vehículos remotos
            val list = vehicleRepo.getMyVehicles(id)

            // Eliminar cada vehículo
            list.forEach { v ->
                v._id?.let { vehicleRepo.deleteVehicle(it) }
            }

            // Cambiar rol a usuario normal
            role.value = "usuario"

            // Actualizar backend
            userRepo.updateUser(
                id,
                UserRemote(
                    _id = id,
                    fullName = fullName.value,
                    phone = phone.value,
                    email = email.value,
                    password = "",   // no se actualiza pass
                    role = "usuario"
                )
            )

            onSuccess()

        } catch (e: Exception) {
            onError(e.message ?: "Error eliminando vehículo(s)")
        }
    }
    fun getUserName(): String {
        return fullName.value.ifBlank { "Usuario" }
    }
    fun getUserEmail(): String {
        return email.value.ifBlank { "email@desconocido.com" }
    }
    fun createVehicle(
        tipo: String,
        patente: String,
        tamano: String,
        capacidad: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {
        try {
            isLoading.value = true

            val uid = remoteUserId.value
            if (uid.isBlank()) {
                onError("Sesión inválida. Vuelve a iniciar sesión.")
                return@launch
            }

            // Construir vehículo remoto
            val body = VehicleRemote(
                _id = null,           // lo crea Mongo
                userId = uid,
                tipo = tipo,
                patente = patente,
                tamano = tamano,
                capacidad = capacidad
            )

            // 1) Crear vehículo
            vehicleRepo.createVehicle(body)

            // 2) Elevar rol a "conductor" (consistencia backend + estado local)
            role.value = "conductor"
            userRepo.updateUser(
                uid,
                UserRemote(
                    _id = uid,
                    fullName = fullName.value,
                    phone = phone.value,
                    email = email.value,
                    password = "",     // no cambiamos password
                    role = "conductor"
                )
            )

            onSuccess()

        } catch (e: Exception) {
            onError(e.message ?: "Error creando vehículo")
        } finally {
            isLoading.value = false
        }
    }


}
