package com.example.fletex.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fletex.ui.viewmodel.AuthViewModel

@Composable
fun EliminarCuentaScreen(
    navController: NavController,
    authViewModel: AuthViewModel   // ⬅️ ESTE VIENE DEL MAIN (EL CORRECTO)
) {

    val userId = authViewModel.userId.value

    if (userId == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE6F4FA)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                "¿Estás seguro que quieres eliminar tu cuenta?",
                fontSize = 22.sp,
                color = Color(0xFF001B4E)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Confirma contraseña") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (error.isNotEmpty())
                Text(error, color = Color.Red, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // CANCELAR
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA856)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }

                Spacer(modifier = Modifier.width(12.dp))

                // CONFIRMAR
                Button(
                    onClick = {
                        authViewModel.deleteUser(
                            id = userId,
                            passwordEntered = password,
                            onSuccess = {
                                navController.navigate("login") {
                                    popUpTo("welcome") { inclusive = true }
                                }
                            },
                            onError = { msg -> error = msg }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDF4E4E)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Confirmar", color = Color.White)
                }
            }
        }
    }
}
