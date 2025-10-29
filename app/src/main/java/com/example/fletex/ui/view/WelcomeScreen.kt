package com.example.fletex.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fletex.R

@Composable
fun WelcomeScreen(navController: NavController) {
    Surface(
        color = Color(0xFFE6F4FA),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Fletex",
                modifier = Modifier.size(120.dp)
            )


            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Bienvenido a fletex",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF001B4E)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933)),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(40.dp)
            ) {
                Text("Iniciar Sesión", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("register") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933)),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(40.dp)
            ) {
                Text("Registrarme", color = Color.White)
            }
        }
    }
}
