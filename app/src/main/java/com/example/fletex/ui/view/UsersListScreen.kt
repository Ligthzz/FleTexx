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
import com.example.fletex.data.local.UserRepository
import com.example.fletex.data.model.User
import kotlinx.coroutines.launch

@Composable
fun UserListScreen(navController: NavController) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { UserRepository(context) }

    var users by remember { mutableStateOf(listOf<User>()) }

    //  Cargar usuarios al iniciar
    LaunchedEffect(Unit) {
        scope.launch {
            users = repository.getAllUsers()
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

            //  Header con botón de volver
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color(0xFF001B4E))
                }

                Text(
                    text = "Usuarios Registrados",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF001B4E)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (users.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay usuarios registrados aún", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(users) { user ->
                        UserCard(user)
                    }
                }
            }
        }
    }
}

@Composable
fun UserCard(user: User) {
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
        Text(text = " ${user.email}", color = Color(0xFF333333))
        Text(text = " ${user.phone}", color = Color(0xFF555555))
    }
}
