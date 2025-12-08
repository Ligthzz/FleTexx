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
import com.example.fletex.data.model.UserRemote
import com.example.fletex.data.repository.RemoteUserRepository
import kotlinx.coroutines.launch

@Composable
fun UserListScreen(navController: NavController) {

    val scope = rememberCoroutineScope()
    val repository = remember { RemoteUserRepository() }

    var users by remember { mutableStateOf<List<UserRemote>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                users = repository.getUsers()
            } catch (e: Exception) {
                errorMessage = "Error cargando usuarios: ${e.message}"
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

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color(0xFF001B4E))
                }

                Text(
                    text = "Usuarios Registrados",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF001B4E),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFFF9933))
                    }
                }

                errorMessage != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = errorMessage!!, color = Color.Red)
                    }
                }

                users.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay usuarios registrados aún", color = Color.Gray)
                    }
                }

                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(users) { user -> UserCard(user) }
                    }
                }
            }
        }
    }
}

@Composable
fun UserCard(user: UserRemote) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = user.fullName,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF001B4E),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = user.email, color = Color(0xFF333333))
        Text(text = user.phone, color = Color(0xFF555555))
    }
}
