plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("kotlin-kapt")
}

android {
    namespace = "com.example.fletex"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.fletex"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation("androidx.navigation:navigation-compose:2.9.5")
    // ViewModel para Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")

// (Opcional) LiveData + Compose
    implementation("androidx.compose.runtime:runtime-livedata")
    // Iconos adicionales de Material (incluye ArrowForwardIos)

    //para las fotos y weas
    // --- 📷 CameraX ---
    implementation("androidx.camera:camera-camera2:1.2.2")
    implementation("androidx.camera:camera-lifecycle:1.2.2")
    implementation("androidx.camera:camera-view:1.2.2")

    // --- 🌍 Location Services ---
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // --- 🔐 Accompanist Permissions (para pedir permisos nativos) ---
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")

    // --- 🖼️ Coil (para cargar imágenes desde URIs o red) ---
    implementation("io.coil-kt:coil-compose:2.2.2")

    // --- ⚙️ Kotlin Coroutines (para tareas asíncronas como GPS o Bluetooth) ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // --- 🧩 Core KTX (extensiones modernas de Android) ---
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material3:material3")

    // --- 🌍 Google Maps Compose ---
    implementation("com.google.maps.android:maps-compose:4.3.3")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // --- 🧱 Room (Base de datos local) ---
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1") //si usas Kotlin KAPT
    implementation("androidx.room:room-ktx:2.6.1")











}