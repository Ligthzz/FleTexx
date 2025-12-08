package com.example.fletex.data.api

import com.example.fletex.data.model.UserRemote
import com.example.fletex.data.model.VehicleRemote
import com.example.fletex.data.model.LoginResponse
import retrofit2.http.*

interface ApiService {

    // ---------- USERS ----------
    @GET("users")
    suspend fun getUsers(): List<UserRemote>

    @POST("users")
    suspend fun createUser(@Body body: UserRemote): UserRemote

    @POST("auth/login")
    suspend fun login(@Body body: Map<String, String>): LoginResponse

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body user: UserRemote): UserRemote

    @HTTP(method = "DELETE", path = "users/{id}", hasBody = true)
    suspend fun deleteUser(
        @Path("id") id: String,
        @Body body: Map<String, String>
    )

    // ---------- VEHICLES ----------
    @GET("vehicles/user/{userId}")
    suspend fun getVehiclesByUser(@Path("userId") userId: String): List<VehicleRemote>

    @POST("vehicles")
    suspend fun createVehicle(@Body body: VehicleRemote): VehicleRemote

    @PUT("vehicles/{id}")
    suspend fun updateVehicle(@Path("id") id: String, @Body body: VehicleRemote): VehicleRemote

    @DELETE("vehicles/{id}")
    suspend fun deleteVehicle(@Path("id") id: String)

    @GET("vehicles")
    suspend fun getAllVehicles(): List<VehicleRemote>

}
