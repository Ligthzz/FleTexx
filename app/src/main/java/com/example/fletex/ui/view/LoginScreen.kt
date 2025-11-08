package com.example.fletex.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fletex.R
import com.example.fletex.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    Surface(color = Color(0xFFE6F4FA), modifier = Modifier.fillMaxSize()) {
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
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = viewModel.password.value,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("¿Olvidaste la contraseña?", color = Color(0xFFFF9933), fontSize = 12.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.login { navController.navigate("home") } },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión", color = Color.White)
            }

            if (viewModel.errorMessage.value.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(viewModel.errorMessage.value, color = Color.Red, fontSize = 14.sp)
            }
        }
    }
}
