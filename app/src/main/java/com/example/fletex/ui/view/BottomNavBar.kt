package com.example.fletex.ui.view

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavBar(navController: NavController, currentRoute: String?) {

    NavigationBar(
        containerColor = Color(0xFFFFFFFF),
        tonalElevation = 4.dp
    ) {
        val items = listOf(
            NavItem("explorar", Icons.Default.Search, "home"),
            NavItem("rutas", Icons.Default.Map, "rutas"),
            NavItem("chat", Icons.Default.Chat, "chat"),
            NavItem("perfil", Icons.Default.Person, "perfil")
        )

        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) Color(0xFF001B4E) else Color(0xFF3C3C3C)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (selected) Color(0xFF001B4E) else Color(0xFF3C3C3C),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}

data class NavItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
)
