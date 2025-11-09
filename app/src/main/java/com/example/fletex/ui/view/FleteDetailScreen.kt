package com.example.fletex.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fletex.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FleteDetailScreen(navController: NavController) {
    Surface(
        color = Color(0xFFE6F4FA),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //  Header con botón de cerrar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Fletex",
                    modifier = Modifier.size(50.dp)
                )

                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //  Título
            Text(
                text = "Descripción del flete",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFF001B4E)
            )

            Spacer(modifier = Modifier.height(20.dp))

            //  Imagen del camión
            Image(
                painter = painterResource(id = R.drawable.cami), // reemplaza con tu imagen de camión
                contentDescription = "Camión",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //  Detalles de ubicación
            Text(
                text = "McWay Falls, Big Sur, California",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF001B4E),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            //  Botones de información
            Button(
                onClick = { /* datos del conductor */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Datos del conductor", color = Color(0xFF001B4E))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* datos del flete */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Datos del flete", color = Color(0xFF001B4E))
            }

            Spacer(modifier = Modifier.height(40.dp))

            //  Botones inferiores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = { navController.navigate("rutaScreen") }
                ) {
                    Text("Obtener dirección", color = Color(0xFFFF3366))
                }

                TextButton(onClick = { /* compartir */ }) {
                    Text("compartir", color = Color(0xFF001B4E))
                }
            }
        }
    }
}
