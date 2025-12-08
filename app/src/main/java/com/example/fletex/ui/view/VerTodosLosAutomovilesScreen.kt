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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fletex.data.model.VehicleRemote
import com.example.fletex.data.repository.RemoteVehicleRepository
import kotlinx.coroutines.launch

@Composable
fun VerTodosLosAutomovilesScreen(
    navController: NavController
) {

    val repo = remember { RemoteVehicleRepository() }
    val scope = rememberCoroutineScope()

    var vehicles by remember { mutableStateOf<List<VehicleRemote>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Cargar todos los vehículos (directo desde Mongo)
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                vehicles = repo.getAllVehicles()
            } catch (e: Exception) {
                errorMessage = "Error cargando vehículos: ${e.message}"
            } finally {
                isLoading = false
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

            // ---------------- HEADER ----------------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color(0xFF001B4E))
                }

                Text(
                    text = "Todos los Automóviles",
                    fontSize = 20.sp,
                    color = Color(0xFF001B4E)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            when {
                isLoading -> {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFFF9933))
                    }
                }

                errorMessage != null -> {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Text(errorMessage!!, color = Color.Red)
                    }
                }

                vehicles.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Text("Aún no hay vehículos registrados", color = Color.Gray)
                    }
                }

                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(vehicles) { vehicle ->
                            VehicleCard(vehicle)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VehicleCard(vehicle: VehicleRemote) {

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
            color = Color(0xFF001B4E),
            fontSize = 18.sp
        )

        Spacer(Modifier.height(6.dp))

        Text("Tipo: ${vehicle.tipo}", color = Color(0xFF333333))
        Text("Tamaño: ${vehicle.tamano}", color = Color(0xFF333333))
        Text("Capacidad: ${vehicle.capacidad}", color = Color(0xFF333333))

        Spacer(Modifier.height(6.dp))

        Text("ID Usuario: ${vehicle.userId}", color = Color.Gray, fontSize = 12.sp)
    }
}
