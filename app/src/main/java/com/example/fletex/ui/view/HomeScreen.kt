package com.example.fletex.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fletex.R
import com.example.fletex.ui.components.FletexScaffold
import com.example.fletex.ui.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val userName = viewModel.getUserName()

    FletexScaffold(
        navController = navController,
        userName = userName
    ) {

        Surface(
            color = Color(0xFFE6F4FA),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
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
                        modifier = Modifier.size(60.dp)
                    )



                }

                Spacer(modifier = Modifier.height(20.dp))

                // Título con nombre dinámico
                Text(
                    text = "Hola", //<-- programar nombre
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF001B4E)
                )

                Text(
                    text = "¿Qué flete te gustaría tomar?",
                    fontSize = 18.sp,
                    color = Color(0xFF001B4E)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Buscador
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Buscar") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Cards de ejemplo
                FleteCard(
                    title = "Swift transport",
                    pickUp = "8:00AM - 10:00AM",
                    dropOff = "2:00PM - 4:00PM",
                    navController = navController
                )

                Spacer(modifier = Modifier.height(16.dp))

                FleteCard(
                    title = "Cargo connect",
                    pickUp = "10:00AM - 12:00PM",
                    dropOff = "2:00PM - 4:00PM",
                    navController = navController
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { navController.navigate("users") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF001B4E)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text("Ver usuarios registrados", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun FleteCard(
    title: String,
    pickUp: String,
    dropOff: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Título
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Horarios
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Recogida", fontSize = 12.sp, color = Color.Gray)
                    Text(pickUp, fontWeight = FontWeight.SemiBold)
                }
                Column {
                    Text("Entrega", fontSize = 12.sp, color = Color.Gray)
                    Text(dropOff, fontWeight = FontWeight.SemiBold)
                }
            }

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navController.navigate("fleteDetail") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                ) {
                    Text("Detalles", color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { /* reservar */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                ) {
                    Text("Tomar ahora", color = MaterialTheme.colorScheme.onSecondary)
                }
            }
        }
    }
}