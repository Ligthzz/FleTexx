package com.example.fletex.data.repository

import com.example.fletex.data.api.RetrofitClient
import com.example.fletex.data.api.RetrofitClient.apiService
import com.example.fletex.data.model.LoginResponse
import com.example.fletex.data.model.UserRemote
import retrofit2.HttpException

class RemoteUserRepository {

    private val api = RetrofitClient.apiService

    suspend fun registerUser(fullName: String, phone: String, email: String, password: String): UserRemote {

        val body = UserRemote(
            _id = null,
            fullName = fullName,
            phone = phone,
            email = email,
            password = password,
            role = "usuario"
        )

        try {
            return api.createUser(body)
        } catch (e: HttpException) {
            if (e.code() == 409) throw Exception("El correo ya está registrado")
            throw Exception("Error del servidor (${e.code()})")
        }
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return api.login(mapOf("email" to email, "password" to password))
    }

    suspend fun updateUser(id: String, body: UserRemote): UserRemote =
        api.updateUser(id, body)

    suspend fun deleteUserRemote(id: String, password: String) {
        api.deleteUser(id, mapOf("password" to password))
    }

    suspend fun getUsers(): List<UserRemote> {
        return api.getUsers()
    }

}
