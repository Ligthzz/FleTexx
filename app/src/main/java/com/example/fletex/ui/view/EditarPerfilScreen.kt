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
    authViewModel: AuthViewModel   // ⬅ SE RECIBE EL VIEWMODEL GLOBAL
) {

    // DATOS DEL USUARIO LOGEADO DESDE EL VIEWMODEL GLOBAL
    val userId = authViewModel.userId.value
    val currentName = authViewModel.fullName.value
    val currentEmail = authViewModel.email.value
    val currentPhone = authViewModel.phone.value ?: ""

    if (userId == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
        return
    }

    // ESTADOS LOCALES
    var fullName by remember { mutableStateOf(currentName) }
    var email by remember { mutableStateOf(currentEmail) }
    var phone by remember { mutableStateOf(currentPhone) }

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

            // ---------- HEADER ----------
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
                    color = Color(0xFF001B4E),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            // ---------- NOMBRE ----------
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

            // ---------- EMAIL ----------
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

            // ---------- TELÉFONO ----------
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    phoneError = !it.matches(Regex("^\\+?\\d{8,15}$"))
                },
                isError = phoneError,
                label = { Text("Número telefónico nuevo") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            if (phoneError) Text("Número no válido", color = Color.Red)

            Spacer(Modifier.height(16.dp))

            // ---------- CONTRASEÑA ----------
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("Nueva contraseña (opcional)") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

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

            // ---------- BOTONES ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier.weight(1f)
                ) { Text("Anular cambios") }

                Spacer(Modifier.width(12.dp))

                Button(
                    onClick = {
                        scope.launch {
                            authViewModel.updateUser(
                                id = userId,
                                fullName = fullName,
                                phone = phone,
                                email = email,
                                password = newPassword,
                                confirmPassword = confirmPassword
                            ) {
                                navController.navigate("perfil") {
                                    popUpTo("perfil") { inclusive = true }
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFFFF9933)),
                    modifier = Modifier.weight(1f)
                ) { Text("Guardar cambios") }
            }

            Spacer(Modifier.height(20.dp))

            if (authViewModel.errorMessage.value.isNotEmpty()) {
                Text(authViewModel.errorMessage.value, color = Color.Red)
            }
        }
    }
}
