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
import com.example.fletex.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel   // ← SE RECIBE EL GLOBAL
) {

    val fullName = authViewModel.fullName.value
    val email = authViewModel.email.value
    val userId = authViewModel.userId.value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE6F4FA)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Fletex",
                modifier = Modifier.size(55.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Perfil",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF001B4E)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.avatar_ejemplo),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEFDFF5))
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (userId != null) {
                Text(
                    fullName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF001B4E)
                )
                Text(email, color = Color.Gray, fontSize = 14.sp)
            } else {
                CircularProgressIndicator(color = Color(0xFFFF9933))
                Text("Cargando perfil...", color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { navController.navigate("editarPerfil") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Text("Editar perfil", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    authViewModel.fullName.value = ""
                    authViewModel.email.value = ""
                    authViewModel.password.value = ""
                    authViewModel.userId.value = null

                    navController.navigate("login") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            ) {
                Text("Cerrar sesión", color = Color(0xFF001B4E))
            }

            TextButton(
                onClick = {
                    navController.navigate("eliminarCuenta")
                }
            ) {
                Text("Eliminar cuenta", color = Color(0xFF001B4E))
            }
        }
    }
}
