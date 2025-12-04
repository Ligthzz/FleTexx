package com.example.fletex.data.local

import android.content.Context
import androidx.room.Room
import com.example.fletex.data.model.User
import com.example.fletex.data.model.Vehicle

class UserRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "fletex_db"
    )        .fallbackToDestructiveMigration() // simple para dev
        .build()

    private val userDao = db.userDao()
    private val vehicleDao = db.vehicleDao()

    suspend fun registerUser(user: User) = userDao.insertUser(user)

    suspend fun loginUser(email: String, password: String): User? = userDao.login(email, password)

    suspend fun getAllUsers(): List<User> = userDao.getAllUsers()

    //  metodo para cargar el perfil según el correo
    suspend fun getUserByEmail(email: String): User? = userDao.getUserByEmail(email)
    //para hacer la actualizacion
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    //para actualizar el rol
    suspend fun updateUserRole(userId: Int, role: String) = userDao.updateUserRole(userId, role)

    //para eliminar usuario
    suspend fun deleteUser(id: Int) = userDao.deleteUser(id)
    //obtener id
    suspend fun getUserById(id: Int) = userDao.getUserById(id)
    //para vehiculos
    suspend fun insertVehicle(vehicle: Vehicle): Long = vehicleDao.insert(vehicle)
    suspend fun vehiclesByUser(userId: Int) = vehicleDao.getByUser(userId)
    suspend fun deleteVehicle(vehicleId: Int) = vehicleDao.deleteById(vehicleId)
    suspend fun deleteVehiclesByUser(userId: Int) = vehicleDao.deleteAllByUser(userId)
    suspend fun hasVehicles(userId: Int): Boolean = vehicleDao.countByUser(userId) > 0

    //obtener vehiculos x id usaurio
    suspend fun getVehiclesByUser(userId: Int): List<Vehicle> {
        return vehicleDao.getByUser(userId)
    }

    // OBTENER TODOS
    suspend fun getAllVehicles(): List<Vehicle> = vehicleDao.getAllVehicles()
// editar veihuclo
suspend fun updateVehicle(vehicle: Vehicle) = vehicleDao.update(vehicle)





}
