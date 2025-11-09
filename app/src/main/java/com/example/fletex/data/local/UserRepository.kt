package com.example.fletex.data.local

import android.content.Context
import androidx.room.Room
import com.example.fletex.data.model.User

class UserRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "fletex_db"
    ).build()

    private val userDao = db.userDao()

    suspend fun registerUser(user: User) = userDao.insertUser(user)

    suspend fun loginUser(email: String, password: String): User? =
        userDao.login(email, password)

    suspend fun getAllUsers(): List<User> = userDao.getAllUsers()
}
