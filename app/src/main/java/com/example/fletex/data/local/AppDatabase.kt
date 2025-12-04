package com.example.fletex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fletex.data.model.User
import com.example.fletex.data.model.Vehicle

@Database(entities = [User::class, Vehicle::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun vehicleDao(): VehicleDao
}