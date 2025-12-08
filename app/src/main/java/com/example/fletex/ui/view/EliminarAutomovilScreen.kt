package com.example.fletex.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fletex.R
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EliminarAutomovilScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val scope = rememberCoroutineScope()
    val snack = remember { SnackbarHostState() }

    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snack) }
    ) { padding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = Color(0xFFE6F4FA)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // HEADER
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }

                    Text(
                        "Eliminar vehículo",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF001B4E),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // AVATAR
                Image(
                    painter = painterResource(id = R.drawable.avatar_ejemplo),
                    contentDescription = "Foto",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEFDFF5))
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "¿Seguro que quieres eliminar\ntu vehículo vinculado?",
                    fontSize = 20.sp,
                    color = Color(0xFF001B4E),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    label = { Text("Ingresa tu contraseña") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Tu cuenta volverá a usuario estándar si no quedan vehículos.",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // CANCELAR
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Cancelar")
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // CONFIRMAR
                    Button(
                        onClick = {
                            if (password.isBlank()) {
                                errorMessage = "Ingresa tu contraseña"
                                return@Button
                            }

                            scope.launch {
                                authViewModel.removeAllVehiclesAndDowngrade(
                                    onSuccess = {
                                        // LÓGICA CORRECTA DE REDIRECCIÓN
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
                                    onError = { msg -> errorMessage = msg }
                                )
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933))
                    ) {
                        Text("Confirmar", color = Color.White)
                    }
                }
            }
        }
    }
}
