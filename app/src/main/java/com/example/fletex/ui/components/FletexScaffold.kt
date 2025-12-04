package com.example.fletex.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.NavController
import com.example.fletex.R
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FletexScaffold(
    navController: NavController,
    userName: String,
    vm: AuthViewModel = viewModel(),
    content: @Composable () -> Unit
) {
    // TOMAR ROL EN VIVO DEL VIEWMODEL
    val role by vm.role

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            FletexDrawer(
                userName = userName,
                role = role,
                onRutasClick = { navController.navigate("rutaScreen") },
                onPerfilClick = { navController.navigate("perfil") },
                onMapaClick = { navController.navigate("home") },
                onChatClick = { navController.navigate("chat") },
                onAjustesClick = { navController.navigate("config") },
                onTrabajaConFletex = { navController.navigate("registrarFlete") }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("FleteX") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "menu")
                        }
                    }
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                content()
            }
        }
    }
}
