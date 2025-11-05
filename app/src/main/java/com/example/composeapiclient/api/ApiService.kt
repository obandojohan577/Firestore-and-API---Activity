// INICIO DEL CÓDIGO CORREGIDO para ApiService.kt
package com.example.composeapiapp.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("users") // Es buena práctica no incluir el primer "/"
    suspend fun createUser(@Body user: User): Response<ApiResponse>

    @GET("users")
    suspend fun getUsers(): Response<List<User>> // La API debería devolver directamente una lista de usuarios
}

// Modelo para la respuesta al crear un usuario
data class ApiResponse(
    val message: String,
    val data: User // Suponiendo que la API devuelve el usuario creado dentro de un objeto 'data'
)

// FIN DEL CÓDIGO CORREGIDO
