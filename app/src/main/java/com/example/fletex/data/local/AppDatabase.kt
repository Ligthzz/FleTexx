package com.example.fletex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fletex.data.model.User
import com.example.fletex.data.model.Vehicle

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}