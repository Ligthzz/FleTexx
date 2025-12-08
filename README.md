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

## **Funcionalidades implementadas**

###  Autenticación y gestión de usuarios
- Registro de usuarios con validaciones por campo.  
- Inicio de sesión con verificación local (Room).  
- Visualización del perfil del usuario y lista de usuarios registrados.  

###  Recursos nativos
- **GPS:** integración con *Google Maps Compose* para mostrar rutas y ubicación en tiempo real.  
- **Cámara:** implementación de *CameraX* para tomar fotografías y previsualizarlas en la interfaz.  

###  Interfaz y usabilidad
- Diseño coherente con **Material Design 3**.  
- Navegación fluida y animada mediante `AnimatedNavHost`.  
- Formularios adaptables a distintos tamaños de pantalla.  
- Retroalimentación visual: loaders, íconos de error, mensajes dinámicos.  


---
## **Funcionalidades principales**
Crear usuarios
Iniciar sesión
Editar perfil
Cambiar contraseña y correo
Agregar vehículo
Editar vehículo
Eliminar vehículo    
Ver vehículos propios
Vista de mapa con GPS
Vista de clima por localidad (API externa)
Render de pantallas según rol (normal / fletero)

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
##  **ARQUITECTURA**

MVVM
>Jetpack Compose
>StateFlow
>Retrofit
>Coroutines
>MongoDB Atlas
>Node.js + Express
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

## Licencia
Este protecto fue desarrollado con fines académicos para la asignatura de Desarrollo de Aplicaciones Móviles en Duoc UC.
Su código puede ser reutilizado.

## Contacto
> Desarrollado por Joscelynne Díaz Zavala; Joaquin Alonso Medina Villa.
>Duoc UC — Ingeniería Informática


