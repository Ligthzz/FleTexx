package com.example.fletex.ui.view

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.fletex.data.local.UserRepository
import com.example.fletex.data.model.User
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPerfilScreen(navController: NavController) {

    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(
        factory = viewModelFactory {
            initializer { AuthViewModel(context.applicationContext as Application) }
        }
    )

    val repository = remember { UserRepository(context) }
    var user by remember { mutableStateOf<User?>(null) }
    val scope = rememberCoroutineScope()

    // cargar usuario ACTUAL
    LaunchedEffect(Unit) {
        val users = repository.getAllUsers()
        if (users.isNotEmpty())
            user = users.last()
    }

    if (user == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // estados locales precargados con datos del usuario
    var fullName by remember { mutableStateOf(user!!.fullName) }
    var email by remember { mutableStateOf(user!!.email) }
    var phone by remember { mutableStateOf(user!!.phone) }

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmError by remember { mutableStateOf(false) }

    Surface(color = Color(0xFFE6F4FA), modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }

                Text(
                    text = "Editar información principal",
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color(0xFF001B4E)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Nombre
            OutlinedTextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    nameError = it.isBlank()
                },
                isError = nameError,
                label = { Text("Nombre nuevo del usuario") },
                modifier = Modifier.fillMaxWidth()
            )
            if (nameError) Text("El nombre no puede estar vacío", color = Color.Red)

            Spacer(Modifier.height(16.dp))

            // Correo
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                },
                isError = emailError,
                label = { Text("Nueva dirección de correo") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            if (emailError) Text("Correo inválido", color = Color.Red)

            Spacer(Modifier.height(16.dp))

            // Teléfono
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    phoneError = !it.matches(Regex("^\\+?\\d{8,15}\$"))
                },
                isError = phoneError,
                label = { Text("Número telefónico nuevo") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            if (phoneError) Text("Número no válido", color = Color.Red)

            Spacer(Modifier.height(16.dp))

            // contraseña nueva (opcional)
            OutlinedTextField(
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    passwordError = false
                },
                label = { Text("Nueva contraseña (opcional)") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // confirmar contraseña (solo si está escribiendo)
            if (newPassword.isNotBlank()) {
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        confirmError = it != newPassword
                    },
                    isError = confirmError,
                    label = { Text("Confirmar contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                if (confirmError) Text("Las contraseñas no coinciden", color = Color.Red)
            }

            Spacer(Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Anular cambios")
                }

                Spacer(Modifier.width(12.dp))

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.updateUser(
                                id = user!!.id,
                                fullName = fullName,
                                phone = phone,
                                email = email,
                                password = newPassword,
                                confirmPassword = confirmPassword,
                            ) {
                                navController.navigate("perfil") {
                                    popUpTo("perfil") { inclusive = true }
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Guardar cambios")
                }
            }

            Spacer(Modifier.height(20.dp))

            if (viewModel.errorMessage.value.isNotEmpty()) {
                Text(
                    viewModel.errorMessage.value,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}
