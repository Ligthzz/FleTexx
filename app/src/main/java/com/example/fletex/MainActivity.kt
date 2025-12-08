package com.example.fletex

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fletex.ui.theme.FletexTheme
import com.example.fletex.ui.viewmodel.AuthViewModel
import com.example.fletex.ui.view.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FletexTheme {

                val navController = rememberNavController()

                //  ViewModel global
                val authViewModel: AuthViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer { AuthViewModel(applicationContext as Application) }
                    }
                )

                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route

                Surface(color = MaterialTheme.colorScheme.background) {

                    Column {

                        Box(modifier = Modifier.weight(1f)) {

                            AnimatedNavHost(
                                navController = navController,
                                startDestination = "welcome",

                                enterTransition = {
                                    slideInHorizontally(
                                        initialOffsetX = { it },
                                        animationSpec = tween(500, easing = FastOutSlowInEasing)
                                    ) + fadeIn(tween(500))
                                },
                                exitTransition = {
                                    slideOutHorizontally(
                                        targetOffsetX = { -300 },
                                        animationSpec = tween(400, easing = FastOutLinearInEasing)
                                    ) + fadeOut(tween(400))
                                }
                            ) {

                                // No necesita ViewModel
                                composable("welcome") { WelcomeScreen(navController) }

                                composable("login") {
                                    LoginScreen(navController, authViewModel)
                                }

                                composable("register") {
                                    RegisterScreen(navController, authViewModel)
                                }

                                composable("home") {
                                    HomeScreen(navController, authViewModel)
                                }

                                // Chat NO usa AuthViewModel → se saca
                                composable("chat") {
                                    ChatScreen(navController)
                                }

                                composable("fleteDetail") {
                                    FleteDetailScreen(navController)
                                }

                                composable("rutaScreen") {
                                    RutaScreen(navController)
                                }

                                composable("users") {
                                    UserListScreen(navController)
                                }

                                composable("perfil") {
                                    ProfileScreen(navController, authViewModel)
                                }

                                composable("editarPerfil") {
                                    EditarPerfilScreen(navController, authViewModel)
                                }

                                composable("registrarFlete") {
                                    RegistrarFleteScreen(navController, authViewModel)
                                }

                                //  ESTA ES LA CORRECCIÓN MÁS IMPORTANTE
                                composable("eliminarCuenta") {
                                    EliminarCuentaScreen(navController, authViewModel)
                                }
                                composable("homeFletero") {
                                    HomeFleteroScreen(navController, authViewModel)
                                }

                                composable("todosAutomoviles") {
                                    VerTodosLosAutomovilesScreen(navController)
                                }
                                composable("editarAutomovil") {
                                    EditarVehiculoScreen(navController, authViewModel)
                                }
                                composable("misVehiculos") {
                                    MisVehiculosScreen(navController, authViewModel)
                                }
                                composable("profileFletero") {
                                    ProfileFleteroScreen(navController, authViewModel)
                                }
                                composable("clima") {
                                    WeatherScreen(navController)
                                }





                            }
                        }

                        if (currentRoute in listOf("home", "chat", "perfil")) {
                            BottomNavBar(navController, currentRoute)
                        }
                    }
                }
            }
        }
    }
}
