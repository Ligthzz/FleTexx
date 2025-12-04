package com.example.fletex.data.local

import androidx.room.*
import com.example.fletex.data.model.Vehicle

@Dao
interface VehicleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: Vehicle): Long

    @Update
    suspend fun update(vehicle: Vehicle)

    @Query("DELETE FROM vehicles WHERE id = :vehicleId")
    suspend fun deleteById(vehicleId: Int)

    @Query("DELETE FROM vehicles WHERE userId = :userId")
    suspend fun deleteAllByUser(userId: Int)

    @Query("SELECT * FROM vehicles WHERE userId = :userId")
    suspend fun getByUser(userId: Int): List<Vehicle>

    @Query("SELECT COUNT(*) FROM vehicles WHERE userId = :userId")
    suspend fun countByUser(userId: Int): Int
    // este es para listar los autos
    @Query("SELECT * FROM vehicles")
    suspend fun getAllVehicles(): List<Vehicle>
}
