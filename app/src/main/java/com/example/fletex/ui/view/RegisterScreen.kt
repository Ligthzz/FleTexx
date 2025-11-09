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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fletex.R
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
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

    //  Estado para animación del botón
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
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Fletex",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Registrarme",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFF001B4E)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ️ Campos de texto
                OutlinedTextField(
                    value = fullName,
                    onValueChange = {
                        fullName = it
                        nameError = it.isBlank()
                    },
                    isError = nameError,
                    label = { Text("Nombre completo") },
                    trailingIcon = {
                        if (nameError) Icon(Icons.Default.Error, contentDescription = "Error", tint = Color.Red)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                if (nameError) Text("El nombre es obligatorio", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        phoneError = !it.matches(Regex("^\\+?\\d{8,15}\$"))
                    },
                    isError = phoneError,
                    label = { Text("Teléfono") },
                    trailingIcon = {
                        if (phoneError) Icon(Icons.Default.Error, contentDescription = "Error", tint = Color.Red)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                if (phoneError) Text("Número no válido", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    },
                    isError = emailError,
                    label = { Text("Correo electrónico") },
                    trailingIcon = {
                        if (emailError) Icon(Icons.Default.Error, contentDescription = "Error", tint = Color.Red)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                if (emailError) Text("Formato de correo inválido", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.length < 6
                    },
                    isError = passwordError,
                    label = { Text("Contraseña") },
                    trailingIcon = {
                        if (passwordError) Icon(Icons.Default.Error, contentDescription = "Error", tint = Color.Red)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                if (passwordError) Text("Debe tener al menos 6 caracteres", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        confirmError = it != password
                    },
                    isError = confirmError,
                    label = { Text("Confirmar contraseña") },
                    trailingIcon = {
                        if (confirmError) Icon(Icons.Default.Error, contentDescription = "Error", tint = Color.Red)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                if (confirmError) Text("Las contraseñas no coinciden", color = Color.Red, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(20.dp))

                //  Botón animado con loader
                Button(
                    onClick = {
                        if (!nameError && !phoneError && !emailError && !passwordError && !confirmError) {
                            isRegistering = true
                            viewModel.register(fullName, phone, email, password, confirmPassword) {
                                scope.launch {
                                    delay(1500)
                                    snackbarHostState.showSnackbar("Registro exitoso 🎉")
                                    isRegistering = false
                                    navController.navigate("login")
                                }
                            }
                        } else {
                            viewModel.errorMessage.value =
                                "Corrige los campos marcados antes de continuar"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(), //  transición suave
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933))
                ) {
                    if (isRegistering) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(4.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Registrarse", color = Color.White)
                    }
                }

                //  Mensaje general
                if (viewModel.errorMessage.value.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(viewModel.errorMessage.value, color = Color.Red, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                //  Enlace para ir al login
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿Ya tienes una cuenta?",
                        color = Color(0xFF001B4E),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    TextButton(onClick = { navController.navigate("login") }) {
                        Text(
                            text = "Inicia sesión",
                            color = Color(0xFFFF9933),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
