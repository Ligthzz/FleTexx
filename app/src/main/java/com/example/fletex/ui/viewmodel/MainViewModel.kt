package com.example.fletex.ui.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //  Cámara
    private val _photoUri = MutableStateFlow<Uri?>(null)
    val photoUri: StateFlow<Uri?> = _photoUri

    fun setPhotoUri(uri: Uri) {
        _photoUri.value = uri
    }

    //  Ubicación (GPS)
    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    @android.annotation.SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        val context = getApplication<Application>()

        //  Verifica permiso antes de iniciar
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            //  Compatibilidad total con todas las versiones
            @Suppress("DEPRECATION")
            val locationRequest = LocationRequest.create().apply {
                interval = 10000L            // cada 10 segundos
                fastestInterval = 5000L      // mínimo cada 5 segundos
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        _location.value = result.lastLocation
                    }
                },
                Looper.getMainLooper()
            )

        } else {
            Toast.makeText(context, "Permiso de ubicación no concedido", Toast.LENGTH_SHORT).show()
        }
    }
}
