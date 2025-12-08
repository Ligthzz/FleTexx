package com.example.fletex.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fletex.data.model.VehicleRemote
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarVehiculoScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val userId = authViewModel.remoteUserId.value
    val scope = rememberCoroutineScope()
    val snack = remember { SnackbarHostState() }

    if (userId.isBlank()) {
        Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
        return
    }

    // ---------- CAMPOS ----------
    var tipo by remember { mutableStateOf("") }
    var patente by remember { mutableStateOf("") }
    var tamano by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf("") }
    var vehiculoId by remember { mutableStateOf<String?>(null) }

    // ---------- ERRORES ----------
    var tipoError by remember { mutableStateOf(false) }
    var patenteError by remember { mutableStateOf(false) }
    var tamanoError by remember { mutableStateOf(false) }
    var capacidadError by remember { mutableStateOf(false) }

    // ---------- CARGAR VEHÍCULO ----------
    LaunchedEffect(Unit) {
        authViewModel.getMyVehiclesRemote(
            onResult = { list ->
                if (list.isNotEmpty()) {
                    val v = list.first()
                    vehiculoId = v._id
                    tipo = v.tipo
                    patente = v.patente
                    tamano = v.tamano
                    capacidad = v.capacidad
                } else {
                    scope.launch { snack.showSnackbar("No se encontró vehículo") }
                }
            },
            onError = { msg ->
                scope.launch { snack.showSnackbar(msg) }
            }
        )
    }

    Scaffold(snackbarHost = { SnackbarHost(snack) }) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .background(Color(0xFFE6F4FA))
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
                .fillMaxSize()
        ) {

            // ---------- HEADER ----------
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
                Text(
                    "Editar automóvil",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF001B4E)
                )
            }

            Spacer(Modifier.height(20.dp))

            // ---------- CAMPOS ----------
            Text("Tipo de flete", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = tipo,
                onValueChange = { tipo = it; tipoError = it.isBlank() },
                isError = tipoError,
                modifier = Modifier.fillMaxWidth()
            )
            if (tipoError) Text("Campo obligatorio", color = Color.Red)

            Spacer(Modifier.height(12.dp))

            Text("Patente", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = patente,
                onValueChange = {
                    patente = it.uppercase()
                    patenteError = it.length < 5
                },
                isError = patenteError,
                modifier = Modifier.fillMaxWidth()
            )
            if (patenteError) Text("Patente inválida", color = Color.Red)

            Spacer(Modifier.height(12.dp))

            Text("Tamaño", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = tamano,
                onValueChange = { tamano = it; tamanoError = it.isBlank() },
                isError = tamanoError,
                modifier = Modifier.fillMaxWidth()
            )
            if (tamanoError) Text("Tamaño obligatorio", color = Color.Red)

            Spacer(Modifier.height(12.dp))

            Text("Capacidad (KG)", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = capacidad,
                onValueChange = {
                    capacidad = it
                    capacidadError = it.toIntOrNull() == null
                },
                isError = capacidadError,
                modifier = Modifier.fillMaxWidth()
            )
            if (capacidadError) Text("Debe ser número", color = Color.Red)

            Spacer(Modifier.height(30.dp))

            // ---------- BOTÓN GUARDAR ----------
            Button(
                onClick = {
                    if (tipoError || patenteError || tamanoError || capacidadError ||
                        tipo.isBlank() || patente.isBlank() || tamano.isBlank() || capacidad.isBlank()
                    ) {
                        scope.launch { snack.showSnackbar("Corrige los errores antes de guardar") }
                        return@Button
                    }

                    val vid = vehiculoId
                    if (vid == null) {
                        scope.launch { snack.showSnackbar("No se pudo identificar el vehículo") }
                        return@Button
                    }

                    val body = VehicleRemote(
                        _id = vid,
                        userId = userId,
                        tipo = tipo.trim(),
                        patente = patente.trim(),
                        tamano = tamano.trim(),
                        capacidad = capacidad.trim()
                    )

                    scope.launch {
                        authViewModel.updateVehicleRemote(
                            vehicle = body,
                            onSuccess = {
                                scope.launch {
                                    snack.showSnackbar("Vehículo actualizado con éxito")
                                }
                            },
                            onError = { msg ->
                                scope.launch { snack.showSnackbar(msg) }
                            }
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFFF9933)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios", color = Color.White)
            }

            Spacer(Modifier.height(20.dp))

            // ---------- BOTÓN ELIMINAR ----------
            Button(
                onClick = {
                    val id = vehiculoId
                    if (id == null) {
                        scope.launch { snack.showSnackbar("No hay vehículo para eliminar") }
                        return@Button
                    }

                    scope.launch {

                        authViewModel.deleteVehicleRemote(
                            vehicleId = id,
                            onSuccess = {

                                // 1) Calcular nuevo rol
                                authViewModel.downgradeRoleIfNoVehicles()

                                // 2) Avisar
                                scope.launch { snack.showSnackbar("Vehículo eliminado") }

                                // 3) Navegar según rol actual
                                if (authViewModel.role.value == "usuario") {
                                    navController.navigate("home") {
                                        popUpTo("homeFletero") { inclusive = true }
                                    }
                                } else {
                                    navController.navigate("homeFletero") {
                                        popUpTo("homeFletero") { inclusive = true }
                                    }
                                }
                            },
                            onError = { msg ->
                                scope.launch { snack.showSnackbar(msg) }
                            }
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFCC4A4A)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar vehículo", color = Color.White)
            }
        }
    }
}
