package com.example.fletex.ui.viewmodel

import android.app.Application
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        val app = Application()
        viewModel = AuthViewModel(app)
    }

    // ----------------------------------------------------
    //              ESTADO INICIAL
    // ----------------------------------------------------

    @Test
    fun estado_inicial_es_correcto() {
        assertEquals("", viewModel.remoteUserId.value)
        assertEquals("", viewModel.fullName.value)
        assertEquals("", viewModel.phone.value)
        assertEquals("", viewModel.email.value)
        assertEquals("usuario", viewModel.role.value)
        assertFalse(viewModel.isLoading.value)
    }

    // ----------------------------------------------------
    //              getUserName()
    // ----------------------------------------------------

    @Test
    fun getUserName_retorna_usuario_si_nombre_esta_vacio() {
        viewModel.fullName.value = ""
        assertEquals("Usuario", viewModel.getUserName())
    }

    @Test
    fun getUserName_retorna_nombre_real_si_existe() {
        viewModel.fullName.value = "Juan Perez"
        assertEquals("Juan Perez", viewModel.getUserName())
    }

    // ----------------------------------------------------
    //              getUserEmail()
    // ----------------------------------------------------

    @Test
    fun getUserEmail_retorna_email_por_defecto_si_esta_vacio() {
        viewModel.email.value = ""
        assertEquals("email@desconocido.com", viewModel.getUserEmail())
    }

    @Test
    fun getUserEmail_retorna_email_real_si_existe() {
        viewModel.email.value = "test@mail.com"
        assertEquals("test@mail.com", viewModel.getUserEmail())
    }

    // ----------------------------------------------------
    //              CAMBIO DE ROL LOCAL
    // ----------------------------------------------------

    @Test
    fun rol_puede_cambiar_localmente() {
        viewModel.role.value = "conductor"
        assertEquals("conductor", viewModel.role.value)
    }

    // ----------------------------------------------------
    //              LIMPIEZA DE DATOS (SIMULADA)
    // ----------------------------------------------------

    @Test
    fun limpieza_de_estado_deja_valores_por_defecto() {
        viewModel.remoteUserId.value = "123"
        viewModel.fullName.value = "Pedro"
        viewModel.email.value = "pedro@mail.com"
        viewModel.phone.value = "123456"
        viewModel.role.value = "conductor"

        // Simulación de limpieza
        viewModel.remoteUserId.value = ""
        viewModel.fullName.value = ""
        viewModel.email.value = ""
        viewModel.phone.value = ""
        viewModel.role.value = "usuario"

        assertEquals("", viewModel.remoteUserId.value)
        assertEquals("", viewModel.fullName.value)
        assertEquals("", viewModel.email.value)
        assertEquals("", viewModel.phone.value)
        assertEquals("usuario", viewModel.role.value)
    }
}
