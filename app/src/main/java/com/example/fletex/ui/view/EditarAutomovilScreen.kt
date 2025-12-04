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
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarVehiculoScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val scope = rememberCoroutineScope()
    val userId = authViewModel.userId.value

    if (userId == null) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // -------- CAMPOS --------
    var tipo by remember { mutableStateOf("") }
    var patente by remember { mutableStateOf("") }
    var tamano by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf("") }

    // -------- ERRORES --------
    var tipoError by remember { mutableStateOf(false) }
    var patenteError by remember { mutableStateOf(false) }
    var tamanoError by remember { mutableStateOf(false) }
    var capacidadError by remember { mutableStateOf(false) }

    val snack = remember { SnackbarHostState() }

    // -------- CARGAR VEHÍCULO --------
    LaunchedEffect(Unit) {
        authViewModel.getMyVehicles { list ->
            if (list.isNotEmpty()) {
                val v = list.first()
                tipo = v.tipo
                patente = v.patente
                tamano = v.tamano
                capacidad = v.capacidad
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snack) }) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .background(Color(0xFFE6F4FA))
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            // ---------------- HEADER ----------------
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }

                Text(
                    "Editar automóvil",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF001B4E),
                    modifier = Modifier.padding(start = 5.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            // ---------------- CAMPOS ----------------

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
                onValueChange = { patente = it; patenteError = it.length < 5 },
                isError = patenteError,
                modifier = Modifier.fillMaxWidth()
            )
            if (patenteError) Text("Formato inválido", color = Color.Red)

            Spacer(Modifier.height(12.dp))

            Text("Tamaño de flete", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = tamano,
                onValueChange = { tamano = it; tamanoError = it.isBlank() },
                isError = tamanoError,
                modifier = Modifier.fillMaxWidth()
            )
            if (tamanoError) Text("Campo obligatorio", color = Color.Red)

            Spacer(Modifier.height(12.dp))

            Text("Capacidad (kg)", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = capacidad,
                onValueChange = { capacidad = it; capacidadError = it.toIntOrNull() == null },
                isError = capacidadError,
                modifier = Modifier.fillMaxWidth()
            )
            if (capacidadError) Text("Debe ser un número", color = Color.Red)

            Spacer(Modifier.height(30.dp))

            // ---------------- BOTONES ----------------

            // GUARDAR CAMBIOS
            Button(
                onClick = {

                    if (tipoError || patenteError || tamanoError || capacidadError ||
                        tipo.isBlank() || patente.isBlank() || tamano.isBlank() || capacidad.isBlank()
                    ) {
                        scope.launch { snack.showSnackbar("Corrige los errores antes de guardar") }
                        return@Button
                    }

                    authViewModel.getMyVehicles { list ->
                        if (list.isNotEmpty()) {
                            val original = list.first()

                            scope.launch {
                                authViewModel.updateVehicle(
                                    original.copy(
                                        tipo = tipo.trim(),
                                        patente = patente.trim(),
                                        tamano = tamano.trim(),
                                        capacidad = capacidad.trim()
                                    )
                                )
                                snack.showSnackbar("Vehículo actualizado")
                            }
                        }
                    }

                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933))
            ) {
                Text("Guardar cambios", color = Color.White)
            }

            Spacer(Modifier.height(20.dp))

            // ELIMINAR VEHÍCULO
            Button(
                onClick = {
                    scope.launch {
                        authViewModel.removeAllVehiclesAndDowngrade {
                            navController.navigate("home") {
                                popUpTo("homeFletero") { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCC4A4A))
            ) {
                Text("Eliminar vehículo", color = Color.White)
            }
        }
    }
}
