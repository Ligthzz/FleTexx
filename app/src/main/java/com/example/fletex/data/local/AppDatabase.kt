package com.example.fletex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fletex.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
