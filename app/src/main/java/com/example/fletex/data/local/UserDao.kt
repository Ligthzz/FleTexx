package com.example.fletex.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fletex.data.model.User
import com.example.fletex.data.model.Vehicle

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
    @Update
    suspend fun updateUser(user: User)

//    @Delete
//    suspend fun deleteUser(user: User)
    // eliminar usuario con el parametro de id
    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUser(id: Int)

//SIRVE PARA EL MOMENTO DE CAMBIAR A CONDUCTOR
    @Query("UPDATE users SET role = :role WHERE id = :userId")
    suspend fun updateUserRole(userId: Int, role: String)
//obtener usuario por id
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): User?


}



