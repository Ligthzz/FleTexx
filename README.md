# **FLETEX — Aplicación Móvil para Gestión de Fletes**

> Aplicación móvil desarrollada en Kotlin + Jetpack Compose, integrada con un backend propio en Node.js / Express / MongoDB, y consumo de API externa para clima y mapas.
Proyecto creado para la Evaluación Parcial 4 – DSY1105, Duoc UC..

---

##  **Integrantes del equipo**

- **Joscelynne Joice Díaz Zavala**
- **Joaquin Alonso Medina Villa**

    
  Ingeniería Informática — Duoc UC  
  Chile  


---

## **Descripción del proyecto**

**Fletex** es una aplicación móvil que permite **conectar conductores y clientes** para gestionar servicios de transporte de manera rápida, moderna y segura.  
Combina un diseño intuitivo, almacenamiento local y funcionalidades nativas para ofrecer una experiencia completa.

El proyecto está desarrollado en **Kotlin** usando **Jetpack Compose** con el patrón **MVVM**, y emplea **Room** para la persistencia local, **Google Maps Compose** para mostrar rutas y **CameraX** para capturar imágenes directamente desde la app.

---

## **Funcionalidades principales**
### Autenticación y usuario
- Registro de usuario
- Inicio de sesión
- Edición de perfil
- Cambio de correo
- Cambio de contraseña
- Eliminación de cuenta

### Vehículos
- Agregar vehículo
- Editar vehículo
- Eliminar vehículo
- Ver vehículos del usuario
- Cambio automático de rol según cantidad de vehículos

### Recursos externos
- API de clima: temperatura y condiciones por localidad
- GPS + Mapa: vista de ubicación del usuari

---


## **Endpoints utilizados (API externa y microservicio)
**

Base URL: https://TU_BACKEND/api

Usuarios
	•	POST /users/register
	•	POST /users/login
	•	GET /users/:id
	•	PUT /users/:id
	•	DELETE /users/:id

Vehículos
	•	POST /vehicles
	•	GET /vehicles/user/:userId
	•	PUT /vehicles/:id
	•	DELETE /vehicles/:id

API Externa Integrada (Clima – OpenWeatherMap)
	•	GET https://api.openweathermap.org/data/2.5/weather?q={city}&appid={API_KEY}
Se muestra temperatura, condiciones y ubicación en la interfaz.

---

##  **Cómo ejecutar el proyecto**

### 1.	Clonar el repositorio:
git clone https://github.com/TU_USUARIO/FleTeX.git
### 2.	Abrir en Android Studio
### 3.	Sincronizar Gradle
### 4.	Ejecutar en emulador o dispositivo físico

Backend
### 1.	Instalar Node.js
### 2.	En la carpeta /backend ejecutar:
npm install
npm run dev

### 3.	Configurar .env con tu cadena de MongoDB Atlas

### 4.- ejecutar app
- Conectar a telefono o hacerlo desde el emulador
- Presiona ▶️ Run ‘app’ en Android Studio

### 5.- probar funcionalidades

Registrar un nuevo usuario.

Iniciar sesión y ver su información en la pantalla de perfil.

Abrir la pantalla Ruta para visualizar tu ubicación.

Probar la cámara desde la pantalla de Chat.

Probar el mapa

Probar el tiempo

Probar logica de vehiculos

Probar validaciones.

Probar la lista de usuarios.

---

## APK Firmado y Llave
En la carpeta /release del repositorio se incluye:
- app-release.apk (APK firmado)
- fletex-key.jks (llave de firma)


---

### validaciones y pruebas hechas por postman

---
## Licencia
Este protecto fue desarrollado con fines académicos para la asignatura de Desarrollo de Aplicaciones Móviles en Duoc UC.
Su código puede ser reutilizado.

## Contacto
> Desarrollado por Joscelynne Díaz Zavala; Joaquin Alonso Medina Villa.
> Duoc UC — Ingeniería Informática


