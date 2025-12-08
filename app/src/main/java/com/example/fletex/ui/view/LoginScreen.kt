package com.example.fletex.ui.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fletex.R
import com.example.fletex.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel   // ← VIENE DESDE MAINACTIVITY
) {

    val snackbarHostState = remember { SnackbarHostState() }


    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->

        Surface(
            color = Color(0xFFE6F4FA),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Fletex",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Iniciar Sesión",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFF001B4E)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // EMAIL
                OutlinedTextField(
                    value = authViewModel.email.value,
                    onValueChange = {
                        authViewModel.email.value = it
                        emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    },
                    isError = emailError,
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    trailingIcon = {
                        if (emailError) Icon(Icons.Default.Error, contentDescription = null, tint = Color.Red)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                if (emailError)
                    Text("Correo inválido", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(12.dp))

                // PASSWORD
                OutlinedTextField(
                    value = authViewModel.password.value,
                    onValueChange = {
                        authViewModel.password.value = it
                        passwordError = it.length < 6
                    },
                    isError = passwordError,
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        if (passwordError) Icon(Icons.Default.Error, contentDescription = null, tint = Color.Red)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                if (passwordError)
                    Text("Mínimo 6 caracteres", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(25.dp))

                // BOTÓN LOGIN
                Button(
                    onClick = {
                        if (!emailError && !passwordError) {

                            authViewModel.loginRemote(
                                email = authViewModel.email.value,
                                password = authViewModel.password.value,
                                onSuccess = {
                                    if (authViewModel.role.value == "conductor") {
                                        navController.navigate("homeFletero") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    } else {
                                        navController.navigate("home") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }
                                },
                                onError = { msg ->
                                    authViewModel.errorMessage.value = msg
                                }
                            )

                        } else {
                            authViewModel.errorMessage.value = "Datos incorrectos"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .animateContentSize(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933))
                ) {
                    if (authViewModel.isLoading.value) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Iniciar Sesión", color = Color.White)
                    }
                }



                // ERROR GENERAL
                if (authViewModel.errorMessage.value.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        authViewModel.errorMessage.value,
                        color = Color.Red,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("¿No tienes cuenta?", color = Color(0xFF001B4E))
                    TextButton(onClick = { navController.navigate("register") }) {
                        Text(
                            "Regístrate aquí",
                            color = Color(0xFFFF9800),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
