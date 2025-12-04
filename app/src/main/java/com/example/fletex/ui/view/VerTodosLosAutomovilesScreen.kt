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
import kotlinx.coroutines.launch

@Composable
fun VerTodosLosAutomovilesScreen(navController: NavController) {

    val context = LocalContext.current
    val repository = remember { UserRepository(context) }
    val scope = rememberCoroutineScope()

    var vehicles by remember { mutableStateOf(listOf<Vehicle>()) }

    // Cargar todos los vehículos
    LaunchedEffect(Unit) {
        scope.launch {
            vehicles = repository.getAllVehicles()
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color(0xFF001B4E))
                }

                Text(
                    text = "Todos los Automóviles",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF001B4E)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (vehicles.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aún no hay vehículos registrados", color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(vehicles) { vehicle ->
                        VehicleCard(vehicle)
                    }
                }
            }
        }
    }
}

@Composable
fun VehicleCard(vehicle: Vehicle) {

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
        Text("Dueño (ID usuario): ${vehicle.userId}", color = Color.Gray, fontSize = 12.sp)
    }
}
