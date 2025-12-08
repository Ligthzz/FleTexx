package com.example.fletex.data.model

data class UserRemote(
    val _id: String? = null,
    val fullName: String,
    val phone: String,
    val email: String,
    val password: String?,
    val role: String
)
