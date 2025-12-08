package com.example.fletex.ui.view

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fletex.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPerfilScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val mongoId = authViewModel.remoteUserId.value

    var fullName by remember { mutableStateOf(authViewModel.fullName.value) }
    var email by remember { mutableStateOf(authViewModel.email.value) }
    var phone by remember { mutableStateOf(authViewModel.phone.value ?: "") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var confirmError by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Surface(color = Color(0xFFE6F4FA), modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
                Text("Editar Perfil", fontSize = 22.sp, color = Color(0xFF001B4E))
            }

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    nameError = it.isBlank()
                },
                label = { Text("Nombre completo") },
                isError = nameError,
                modifier = Modifier.fillMaxWidth()
            )
            if (nameError) Text("El nombre es obligatorio", color = Color.Red)

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                },
                label = { Text("Correo electrónico") },
                isError = emailError,
                modifier = Modifier.fillMaxWidth()
            )
            if (emailError) Text("Correo inválido", color = Color.Red)

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    phoneError = !it.matches(Regex("^\\+?\\d{8,15}$"))
                },
                label = { Text("Teléfono") },
                isError = phoneError,
                modifier = Modifier.fillMaxWidth()
            )
            if (phoneError) Text("Teléfono inválido", color = Color.Red)

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("Nueva contraseña (opcional)") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (newPassword.isNotBlank()) {
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        confirmError = it != newPassword
                    },
                    label = { Text("Confirmar contraseña") },
                    isError = confirmError,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                if (confirmError) Text("Las contraseñas no coinciden", color = Color.Red)
            }

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = {
                    scope.launch {
                        authViewModel.updateUserRemote(
                            fullName = fullName,
                            phone = phone,
                            email = email,
                            newPassword = if (newPassword.isBlank()) null else newPassword,
                            onSuccess = {
                                navController.navigate("perfil") {
                                    popUpTo("perfil") { inclusive = true }
                                }
                            },
                            onError = { msg -> authViewModel.errorMessage.value = msg }
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFFF9933)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios", color = Color.White)
            }


            if (authViewModel.errorMessage.value.isNotEmpty()) {
                Spacer(Modifier.height(20.dp))
                Text(authViewModel.errorMessage.value, color = Color.Red)
            }
        }
    }
}

