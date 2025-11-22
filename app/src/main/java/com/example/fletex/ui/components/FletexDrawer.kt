package com.example.fletex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fletex.R

@Composable
fun FletexDrawer(
    userName: String,
    onRutasClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onMapaClick: () -> Unit,
    onChatClick: () -> Unit,
    onAjustesClick: () -> Unit,
    onTrabajaClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(Color(0xFFE3F2FD))
            .padding(20.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.avatar_ejemplo),
            contentDescription = "User Photo",
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = userName,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "San Francisco", //CAMBIAR
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        DrawerItem("Rutas", R.drawable.location, onRutasClick)
        DrawerItem("Perfil", R.drawable.avatar_ejemplo, onPerfilClick)
        DrawerItem("Mapa", R.drawable.mapa, onMapaClick)
        DrawerItem("Chat", R.drawable.chat, onChatClick)
        DrawerItem("Ajustes", R.drawable.setting, onAjustesClick)

        Spacer(modifier = Modifier.height(20.dp))

        DrawerItem("Trabaja con FleteX", R.drawable.ic_fletex, onTrabajaClick)
    }
}

@Composable
fun DrawerItem(title: String, icon: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = title,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = title, fontSize = 18.sp)
    }
}
