package com.example.fletex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fletex.ui.theme.FletexTheme
import com.example.fletex.ui.view.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable



class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplica tu tema global
            FletexTheme {
                val navController = rememberNavController()
                val currentBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry.value?.destination?.route

                // Fondo base del tema
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column {
                        // 🔹 Contenedor principal de navegación con animaciones
                        Box(modifier = Modifier.weight(1f)) {
                            AnimatedNavHost(
                                navController = navController,
                                startDestination = "welcome",
                                enterTransition = {
                                    slideInHorizontally(
                                        initialOffsetX = { fullWidth -> fullWidth },
                                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                                    ) + fadeIn(animationSpec = tween(500))
                                },
                                exitTransition = {
                                    slideOutHorizontally(
                                        targetOffsetX = { -300 },
                                        animationSpec = tween(durationMillis = 400, easing = FastOutLinearInEasing)
                                    ) + fadeOut(animationSpec = tween(400))
                                },
                                popEnterTransition = {
                                    slideInHorizontally(
                                        initialOffsetX = { -300 },
                                        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
                                    ) + fadeIn(animationSpec = tween(400))
                                },
                                popExitTransition = {
                                    slideOutHorizontally(
                                        targetOffsetX = { fullWidth -> fullWidth },
                                        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
                                    ) + fadeOut(animationSpec = tween(500))
                                }
                            ) {
                                composable("welcome") { WelcomeScreen(navController) }
                                composable("login") { LoginScreen(navController) }
                                composable("register") { RegisterScreen(navController) }
                                composable("home") { HomeScreen(navController) }
                                composable("chat") { ChatScreen(navController) }
                                composable("fleteDetail") { FleteDetailScreen(navController) }
                                composable("rutaScreen") { RutaScreen(navController) }
                                composable("users") { UserListScreen(navController) }
                                composable("perfil") { ProfileScreen(navController) }

                            }

                        }

                            //  Barra inferior visible solo en pantallas principales
                        if (currentRoute in listOf("home", "chat", "perfil")) {
                            BottomNavBar(navController = navController, currentRoute = currentRoute)
                        }
                    }
                }
            }
        }
    }
}
