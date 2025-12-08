package com.example.fletex.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fletex.R
import com.example.fletex.data.model.VehicleRemote
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileFleteroScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val scope = rememberCoroutineScope()

    val userId = authViewModel.remoteUserId.value
    val fullName = authViewModel.fullName.value
    val email = authViewModel.email.value

    var vehicle by remember { mutableStateOf<VehicleRemote?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    // --- Cargar vehículo del usuario desde MongoDB ---
    LaunchedEffect(userId) {
        if (userId.isNotBlank()) {
            authViewModel.getMyVehiclesRemote(
                onResult = { list -> vehicle = list.firstOrNull() },
                onError = { msg -> errorMessage = msg }
            )
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE6F4FA)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // LOGO
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Fletex",
                modifier = Modifier.size(55.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Perfil del Fletero",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF001B4E)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // FOTO
            Image(
                painter = painterResource(id = R.drawable.avatar_ejemplo),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEFDFF5))
            )

            Spacer(modifier = Modifier.height(20.dp))

            // DATOS BASICOS
            Text(fullName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF001B4E))
            Text(email, fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(30.dp))

            // ===== INFORMACIÓN DEL VEHÍCULO =====
            when {
                errorMessage.isNotEmpty() -> {
                    Text(errorMessage, color = Color.Red)
                }

                vehicle == null -> {
                    CircularProgressIndicator(color = Color(0xFFFF9933))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Cargando vehículo...", color = Color.Gray)
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        Text("Mi vehículo", fontWeight = FontWeight.Bold, color = Color(0xFF001B4E), fontSize = 18.sp)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Tipo: ${vehicle!!.tipo}")
                        Text("Patente: ${vehicle!!.patente}")
                        Text("Tamaño: ${vehicle!!.tamano}")
                        Text("Capacidad: ${vehicle!!.capacidad} kg")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // BOTÓN EDITAR VEHÍCULO
                    Button(
                        onClick = { navController.navigate("editarAutomovil") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Text("Editar automóvil", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // BOTÓN ELIMINAR VEHÍCULO
                    Button(
                        onClick = {
                            scope.launch {
                                authViewModel.downgradeRoleIfNoVehicles()
                                navController.navigate("home") {
                                    popUpTo("homeFletero") { inclusive = true }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Text("Eliminar vehículo", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // BOTÓN CERRAR SESIÓN
            TextButton(
                onClick = {
                    authViewModel.remoteUserId.value = ""
                    authViewModel.fullName.value = ""
                    authViewModel.email.value = ""
                    authViewModel.phone.value = ""
                    authViewModel.role.value = "usuario"

                    navController.navigate("login") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            ) {
                Text("Cerrar sesión", color = Color(0xFF001B4E))
            }

            // BOTÓN ELIMINAR CUENTA
            TextButton(
                onClick = { navController.navigate("eliminarCuenta") }
            ) {
                Text("Eliminar cuenta", color = Color(0xFF001B4E))
            }
        }
    }
}
