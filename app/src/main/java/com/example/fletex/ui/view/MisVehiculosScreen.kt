package com.example.fletex.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fletex.data.local.UserRepository
import com.example.fletex.data.model.Vehicle
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun MisVehiculosScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val context = LocalContext.current
    val repository = remember { UserRepository(context) }
    val scope = rememberCoroutineScope()

    val userId = authViewModel.userId.value

    var vehicles by remember { mutableStateOf(listOf<Vehicle>()) }

    // Cargar SOLO los vehículos del usuario logeado
    LaunchedEffect(userId) {
        if (userId != null) {
            scope.launch {
                vehicles = repository.vehiclesByUser(userId)
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE6F4FA)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color(0xFF001B4E)
                    )
                }

                Text(
                    text = "Mis Vehículos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF001B4E),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (vehicles.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No tienes vehículos registrados", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(vehicles) { vehicle ->
                        VehicleCardUsuario(vehicle)
                    }
                }
            }
        }
    }
}

@Composable
fun VehicleCardUsuario(vehicle: Vehicle) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {

        Text(
            text = "Patente: ${vehicle.patente}",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF001B4E)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text("Tipo: ${vehicle.tipo}", color = Color(0xFF333333))
        Text("Tamaño: ${vehicle.tamano}", color = Color(0xFF333333))
        Text("Capacidad: ${vehicle.capacidad}", color = Color(0xFF333333))
    }
}
