package com.example.fletex.ui.view

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.fletex.R
import com.example.fletex.data.local.UserRepository
import com.example.fletex.data.model.User
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current

    //  ViewModel conectado a Room
    val viewModel: AuthViewModel = viewModel(
        factory = viewModelFactory {
            initializer { AuthViewModel(context.applicationContext as Application) }
        }
    )

    val repository = remember { UserRepository(context) }
    var user by remember { mutableStateOf<User?>(null) }
    val scope = rememberCoroutineScope()

    //funciona momental
    LaunchedEffect(Unit) {
        val allUsers = repository.getAllUsers()
        if (allUsers.isNotEmpty()) {
            user = allUsers.last() // obtiene el último usuario registrado o logueado
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
            verticalArrangement = Arrangement.Top
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Fletex",
                    modifier = Modifier.size(55.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Perfil",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF001B4E)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Imagen circular
            Image(
                painter = painterResource(id = R.drawable.avatar_ejemplo),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEFDFF5))
            )

            Spacer(modifier = Modifier.height(20.dp))

            //  Mostrar datos del usuario real
            if (user != null) {
                Text(
                    text = user!!.fullName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF001B4E)
                )
                Text(text = user!!.email, color = Color.Gray, fontSize = 14.sp)
                Text(text = user!!.phone, color = Color.Gray, fontSize = 14.sp)
            } else {
                CircularProgressIndicator(
                    color = Color(0xFFFF9933),
                    modifier = Modifier.size(40.dp)
                )
                Text("Cargando perfil...", color = Color.Gray)
            }


            Spacer(modifier = Modifier.height(30.dp))

            // Botón editar perfil
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

            // Cerrar sesión
            TextButton(
                onClick = {
                    scope.launch {
                        viewModel.fullName.value = ""
                        viewModel.email.value = ""
                        viewModel.password.value = ""
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                }
            ) {
                Text("Cerrar sesión", color = Color(0xFF001B4E))
            }
        }
    }
}
