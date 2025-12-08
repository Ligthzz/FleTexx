package com.example.fletex.ui.view

import android.app.Application
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.fletex.R
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmError by remember { mutableStateOf(false) }

    var isRegistering by remember { mutableStateOf(false) }

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
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Registrarme",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFF001B4E)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = fullName,
                    onValueChange = {
                        fullName = it
                        nameError = it.isBlank()
                    },
                    isError = nameError,
                    label = { Text("Nombre completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (nameError) Text("Campo obligatorio", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        phoneError = !it.matches(Regex("^\\+?\\d{8,15}$"))
                    },
                    isError = phoneError,
                    label = { Text("Teléfono") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
                if (phoneError) Text("Número inválido", color = Color.Red)

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()

                        email = it
                    },
                    isError = emailError,
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
                if (emailError) Text("Email inválido", color = Color.Red)

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.length < 6
                    },
                    isError = passwordError,
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (passwordError) Text("Debe tener mínimo 6 caracteres", color = Color.Red)

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        confirmError = it != password
                    },
                    isError = confirmError,
                    label = { Text("Confirmar contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (confirmError) Text("Las contraseñas no coinciden", color = Color.Red)

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (!nameError && !phoneError && !emailError && !passwordError && !confirmError) {

                            isRegistering = true

                            viewModel.registerRemote(
                                fullName = fullName,
                                phone = phone,
                                email = email,
                                password = password,
                                onSuccess = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Registro exitoso 🎉")
                                        isRegistering = false
                                        navController.navigate("login")
                                    }
                                },
                                onError = { msg ->
                                    viewModel.errorMessage.value = msg
                                    isRegistering = false
                                }
                            )

                        } else {
                            viewModel.errorMessage.value = "Corrige los campos marcados"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933))
                ) {
                    if (isRegistering) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Registrarse", color = Color.White)
                    }
                }

                if (viewModel.errorMessage.value.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    Text(viewModel.errorMessage.value, color = Color.Red)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Text("¿Ya tienes cuenta?", color = Color(0xFF001B4E))
                    TextButton(onClick = { navController.navigate("login") }) {
                        Text("Inicia sesión", color = Color(0xFFFF9933))
                    }
                }
            }
        }
    }
}

