package com.example.fletex.ui.view

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fletex.ui.viewmodel.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun RutaScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {

    val locationPermission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val location by viewModel.location.collectAsState()

    //  Solicitar permiso y activar actualizaciones cuando esté concedido
    LaunchedEffect(locationPermission.status.isGranted) {
        if (locationPermission.status.isGranted) {
            viewModel.startLocationUpdates()
        } else {
            locationPermission.launchPermissionRequest()
        }
    }

    //  Coordenadas fijas en San Bernardo
    val startLatLng = LatLng(-33.5939, -70.6996) // Plaza de San Bernardo
    val endLatLng = LatLng(-33.5745, -70.7068)   // Av. Portales

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startLatLng, 13f)
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
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
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
                    text = "Trayecto",
                    fontSize = 22.sp,
                    color = Color(0xFF001B4E),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Campos de dirección
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                OutlinedTextField(
                    value = "Av. Colón 1120, San Bernardo",
                    onValueChange = {},
                    label = { Text("Destino de inicio") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = "Av. Portales 2785, San Bernardo",
                    onValueChange = {},
                    label = { Text("Destino final") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mapa
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = location != null),
                    uiSettings = MapUiSettings(zoomControlsEnabled = false)
                ) {
                    Marker(
                        state = MarkerState(position = startLatLng),
                        title = "Inicio",
                        snippet = "Av. Colón 1120"
                    )
                    Marker(
                        state = MarkerState(position = endLatLng),
                        title = "Destino",
                        snippet = "Av. Portales 2785"
                    )

                    //  Ubicación actual (si está activa)
                    location?.let {
                        Marker(
                            state = MarkerState(position = LatLng(it.latitude, it.longitude)),
                            title = "Tu ubicación actual"
                        )
                    }

                    // Línea entre puntos
                    Polyline(
                        points = listOf(startLatLng, endLatLng),
                        color = Color(0xFF001B4E),
                        width = 6f
                    )
                }
            }
        }
    }
}
