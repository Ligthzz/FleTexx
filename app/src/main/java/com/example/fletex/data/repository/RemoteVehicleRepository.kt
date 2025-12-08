package com.example.fletex.data.repository

import com.example.fletex.data.api.RetrofitClient
import com.example.fletex.data.model.VehicleRemote

class RemoteVehicleRepository {

    private val api = RetrofitClient.apiService

    suspend fun getMyVehicles(userId: String): List<VehicleRemote> =
        api.getVehiclesByUser(userId)

    suspend fun createVehicle(vehicle: VehicleRemote): VehicleRemote =
        api.createVehicle(vehicle)

    suspend fun updateVehicle(id: String, vehicle: VehicleRemote): VehicleRemote =
        api.updateVehicle(id, vehicle)

    suspend fun deleteVehicle(id: String) {
        api.deleteVehicle(id)
    }
    suspend fun getAllVehicles(): List<VehicleRemote> {
        return api.getAllVehicles()
    }

}
