package com.example.fletex.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.fletex.ui.components.FletexDrawer
import com.example.fletex.ui.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val userName = authViewModel.fullName.value


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            FletexDrawer(
                userName = userName,
                onRutasClick = { navController.navigate("rutas") },
                onPerfilClick = { navController.navigate("perfil") },
                onMapaClick = { navController.navigate("mapa") },
                onChatClick = { navController.navigate("chat") },
                onAjustesClick = { navController.navigate("ajustes") },
                onTrabajaClick = { navController.navigate("trabajaFletex") }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("FleteX") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                // Aquí puedes poner tu Home real
                Text("Pantalla principal con menú hamburguesa")
            }
        }
    }
}
