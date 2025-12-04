package com.example.fletex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,                // dueño (User.id)
    val tipo: String,
    val patente: String,
    val tamano: String,
    val capacidad: String
)
