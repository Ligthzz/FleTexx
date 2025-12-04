package com.example.fletex.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarFleteScreen(
    navController: NavController,
    vm: AuthViewModel
) {

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
    val scope = rememberCoroutineScope()
    var dialogOk by remember { mutableStateOf(false) }

    // REGEX patentes Chile (AABB12 o AA•BB•12)
    val patenteRegex = Regex("^[A-Za-z]{2}\\s?[A-Za-z]{2}\\s?\\d{2}\$")

    Scaffold(snackbarHost = { SnackbarHost(snack) }) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Registrar flete", fontSize = 24.sp, color = Color(0xFF001B4E))
            Spacer(Modifier.height(18.dp))

            // -------- TIPO --------
            OutlinedTextField(
                value = tipo,
                onValueChange = {
                    tipo = it
                    tipoError = it.isBlank()
                },
                isError = tipoError,
                label = { Text("Tipo de flete") },
                modifier = Modifier.fillMaxWidth()
            )
            if (tipoError) Text("Este campo es obligatorio", color = Color.Red, fontSize = 12.sp)

            Spacer(Modifier.height(10.dp))

            // -------- PATENTE --------
            OutlinedTextField(
                value = patente,
                onValueChange = {
                    patente = it
                    patenteError = !patenteRegex.matches(it.replace("•", "").replace("-", ""))
                },
                isError = patenteError,
                label = { Text("Patente (Ej: AA BB 12)") },
                modifier = Modifier.fillMaxWidth()
            )
            if (patenteError) Text("Formato inválido de patente", color = Color.Red, fontSize = 12.sp)

            Spacer(Modifier.height(10.dp))

            // -------- TAMAÑO --------
            OutlinedTextField(
                value = tamano,
                onValueChange = {
                    tamano = it
                    tamanoError = it.isBlank()
                },
                isError = tamanoError,
                label = { Text("Tamaño del flete") },
                modifier = Modifier.fillMaxWidth()
            )
            if (tamanoError) Text("Este campo es obligatorio", color = Color.Red, fontSize = 12.sp)

            Spacer(Modifier.height(10.dp))

            // -------- CAPACIDAD --------
            OutlinedTextField(
                value = capacidad,
                onValueChange = {
                    capacidad = it
                    capacidadError = it.toIntOrNull() == null
                },
                isError = capacidadError,
                label = { Text("Capacidad (en kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            if (capacidadError) Text("Debe ser un número válido", color = Color.Red, fontSize = 12.sp)

            Spacer(Modifier.height(24.dp))

            // -------- BOTÓN REGISTRAR --------
            Button(
                onClick = {
                    if (tipoError || patenteError || tamanoError || capacidadError ||
                        tipo.isBlank() || patente.isBlank() || tamano.isBlank() || capacidad.isBlank()
                    ) {
                        scope.launch { snack.showSnackbar("Corrige los errores antes de continuar") }
                        return@Button
                    }

                    vm.registerVehicle(
                        tipo.trim(),
                        patente.trim(),
                        tamano.trim(),
                        capacidad.trim(),
                        onSuccess = {
                            dialogOk = true
                            navController.navigate("homeFletero") {
                                popUpTo("home") { inclusive = true }
                            }
                        },
                        onError = { msg -> scope.launch { snack.showSnackbar(msg) } }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933))
            ) {
                Text("Registrar flete", color = Color.White)
            }
        }

        // -------- DIALOG --------
        if (dialogOk) {
            AlertDialog(
                onDismissRequest = { dialogOk = false },
                confirmButton = {
                    TextButton(onClick = { dialogOk = false }) {
                        Text("Ok")
                    }
                },
                title = { Text("¡Listo!") },
                text = { Text("Vehículo registrado exitosamente") }
            )
        }
    }
}
