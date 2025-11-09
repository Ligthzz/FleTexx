package com.example.fletex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, //esto es para la primary ki
    val fullName: String,
    val phone: String,
    val email: String,
    val password: String
)
