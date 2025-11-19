# **FLETEX — Aplicación Móvil para Gestión de Fletes**

> Proyecto desarrollado en **Android Studio (Kotlin + Jetpack Compose)** aplicando arquitectura **MVVM**, persistencia local con **Room**, e integración de recursos nativos como **GPS y Cámara**.

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

###  Persistencia local
- Base de datos **Room** con entidad `User`.  
- `UserRepository` que centraliza operaciones CRUD (`insert`, `select`, etc.).  

###  Gestión del estado
- Uso de `State`, `mutableStateOf` y `StateFlow` para sincronizar la UI con la lógica de negocio.  
- Estados visuales que responden a acciones del usuario en tiempo real (por ejemplo, mostrar loader durante el login).  

---

##  **Estructura del proyecto**

### Arquitectura MVVM

- **Model**  Define la estructura de datos ->  `User.kt` 
- **Repository**  Gestiona la comunicación con Room -> `UserRepository.kt`, `UserDao.kt` 
- **ViewModel**  Contiene la lógica de negocio y los estados -> `AuthViewModel.kt`, `MainViewModel.kt` 
- **View (UI)** Renderiza la interfaz con Jetpack Compose -> `LoginScreen.kt`, `RegisterScreen.kt`, `ProfileScreen.kt`, etc. 

---

## 🚀 **Cómo ejecutar el proyecto**

### 1.- Clonar el repositorio
```bash
git clone https://github.com/tuusuario/Fletex.git
```
### 2.- Abrir en Android Studio

Abrir la carpeta del proyecto.

Esperar a que Gradle sincronice automáticamente las dependencias.

### 3.- Configurar la API Key de Google Maps

Edita el archivo AndroidManifest.xml e inserta tu API Key:

```bash
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="TU_API_KEY_AQUI" />
```
### 4.- ejecutar app
- Conectar a telefono o hacerlo desde el emulador
- Presiona ▶️ Run ‘app’ en Android Studio

### 5.- probar funcionalidades

Registrar un nuevo usuario.

Iniciar sesión y ver su información en la pantalla de perfil.

Abrir la pantalla Ruta para visualizar tu ubicación.

Probar la cámara desde la pantalla de Chat.

Probar validaciones.

Probar la lista de usuarios.

---

## Tecnologias utilizadas
- lenguaje: kotlin
- Arquitectura: MVVM
- Persistencia: Room (SQLite local)
- Navegación:	Accompanist Navigation Animation
- Recursos nativos:	CameraX, Google Maps Compose
- Lógica asíncrona:	Kotlin Coroutines + StateFlow
- Carga de imágenes:	Coil

---

## Licencia
Este protecto fue desarrollado con fines académicos para la asignatura de Desarrollo de Aplicaciones Móviles en Duoc UC.
Su código puede ser reutilizado.

## Contacto
>Desarrollado por Joscelynne Díaz Zavala; Joaquin Alonso Medina Villa; Valentina Ignacia Morales Figueroa.
>Duoc UC — Ingeniería Informática


