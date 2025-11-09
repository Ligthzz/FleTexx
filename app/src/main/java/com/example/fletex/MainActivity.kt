package com.example.fletex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fletex.ui.view.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val currentBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry.value?.destination?.route

            Surface {
                Column {
                    // 🔹 Contenedor de navegación principal
                    Box(modifier = Modifier.weight(1f)) {
                        NavHost(
                            navController = navController,
                            startDestination = "welcome"
                        ) {
                            composable("welcome") { WelcomeScreen(navController) }
                            composable("login") { LoginScreen(navController) }
                            composable("register") { RegisterScreen(navController) }
                            composable("home") { HomeScreen(navController) }
                            composable("fleteDetail") { FleteDetailScreen(navController) }
                            composable("chat") { ChatScreen(navController) }
                            composable("rutaScreen") { RutaScreen(navController) } // ✅ CORRECTO
                            composable("users") { UserListScreen(navController) }

                        }
                    }

                    // 🔹 Barra inferior visible solo en pantallas principales
                    if (currentRoute in listOf("home", "chat", "perfil")) {
                        BottomNavBar(navController = navController, currentRoute = currentRoute)
                    }
                }
            }
        }
    }
}
