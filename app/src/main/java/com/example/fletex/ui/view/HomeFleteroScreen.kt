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
import androidx.navigation.NavController
import com.example.fletex.R
import com.example.fletex.ui.components.FletexScaffold
import com.example.fletex.ui.viewmodel.AuthViewModel

@Composable
fun HomeFleteroScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val userName = authViewModel.fullName.value.ifBlank { "Conductor" }

    FletexScaffold(
        navController = navController,
        userName = userName
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ---------- HEADER ----------
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo FleteX",
                        modifier = Modifier.size(60.dp)
                    )

                    IconButton(onClick = { /* menú futuro */ }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ---------- TÍTULO ----------
                Text(
                    text = "Hola ¿Qué haremos hoy?",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(35.dp))


                // ------------ BOTÓN CREAR RUTA ------------
                Button(
                    onClick = { navController.navigate("crearRuta") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Crear ruta", color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))


                // ------------ BOTÓN VER FLETES ------------
                Button(
                    onClick = { navController.navigate("verFletes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Ver fletes", color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))


                // ------------ BOTÓN EDITAR AUTOMÓVIL ------------
                Button(
                    onClick = { navController.navigate("editarAutomovil") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Editar automóvil", color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))


                // ------------ BOTÓN MIS VEHÍCULOS ------------
                Button(
                    onClick = { navController.navigate("misVehiculos") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Mis vehículos", color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))


                //  ------------ BOTÓN NUEVO: VER TODOS LOS VEHÍCULOS ------------
                Button(
                    onClick = { navController.navigate("todosAutomoviles") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Ver todos los automóviles", color = Color.White)
                }
            }
        }
    }
}
