package com.example.fletex.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fletex.ui.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    navController: NavController
) {
    val vm: WeatherViewModel = viewModel()

    var city by remember { mutableStateOf("Santiago") }

    val weather = vm.weather.value
    val isLoading = vm.isLoading.value
    val error = vm.error.value

    LaunchedEffect(Unit) {
        vm.loadWeather(city)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        // ---------- TOP BAR (solo flecha + título a la izquierda) ----------
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color(0xFF001B4E)
                )
            }

            Text(
                text = "Clima actual",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF001B4E)
            )
        }

        Spacer(Modifier.height(20.dp))

        // ---------- CONTENIDO CENTRADO ----------
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Esto es fundamental para saber si es el clima perfecto para tu viaje",
                fontSize = 18.sp,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Ciudad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = { vm.loadWeather(city) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933)),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Buscar clima", color = Color.White)
            }

            Spacer(Modifier.height(20.dp))

            when {
                isLoading -> {
                    CircularProgressIndicator(color = Color(0xFFFF9933))
                }

                error.isNotEmpty() -> {
                    Text(error, color = Color.Red)
                }

                weather != null -> {
                    WeatherInfo(weather)
                }
            }
        }
    }
}
@Composable
fun WeatherInfo(weather: com.example.fletex.data.model.WeatherResponse) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = weather.name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF001B4E)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "${weather.main.temp}°C",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFFF9933)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = weather.weather.firstOrNull()?.description?.replaceFirstChar { it.uppercase() }
                ?: "",
            fontSize = 18.sp,
            color = Color.DarkGray
        )
    }
}
