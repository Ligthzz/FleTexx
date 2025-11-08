package com.example.fletex.ui.view

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fletex.R
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
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

                OutlinedTextField(
                    value = viewModel.email.value,
                    onValueChange = {
                        viewModel.onEmailChange(it)
                        emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    },
                    isError = emailError,
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    trailingIcon = {
                        if (emailError) Icon(Icons.Default.Error, contentDescription = "Error", tint = Color.Red)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                if (emailError)
                    Text("Formato de correo inválido", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = viewModel.password.value,
                    onValueChange = {
                        viewModel.onPasswordChange(it)
                        passwordError = it.length < 6
                    },
                    isError = passwordError,
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        if (passwordError) Icon(Icons.Default.Error, contentDescription = "Error", tint = Color.Red)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                if (passwordError)
                    Text("Debe tener al menos 6 caracteres", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(8.dp))
                Text("¿Olvidaste la contraseña?", color = Color(0xFFFF9933), fontSize = 12.sp)

                Spacer(modifier = Modifier.height(16.dp))

                // 🧡 Botón de inicio de sesión
                Button(
                    onClick = {
                        if (!emailError && !passwordError) {
                            viewModel.login {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Bienvenido ${viewModel.fullName.value.ifBlank { "👋" }}")
                                    delay(1200)
                                    navController.navigate("home")
                                }
                            }
                        } else {
                            viewModel.errorMessage.value =
                                "Por favor corrige los campos antes de continuar"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Iniciar Sesión", color = Color.White)
                }

                // ⚠️ Mensaje general de error
                if (viewModel.errorMessage.value.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        viewModel.errorMessage.value,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }

                //  Texto para ir al registro
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿No tienes cuenta?",
                        color = Color(0xFF001B4E),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    TextButton(onClick = { navController.navigate("register") }) {
                        Text(
                            text = "Regístrate aquí",
                            color = Color(0xFFFF9800),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
