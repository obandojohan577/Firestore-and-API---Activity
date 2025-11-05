// INICIO DEL CÓDIGO para User.kt
package com.example.composeapiapp.data

data class User(
    // Asegúrate que los nombres coincidan con los de tu API JSON
    // Si tu API devuelve 'id', puedes dejarlo así.
    // Si no lo devuelve al crear, puede ser nulo.
    val id: Int? = null,
    val name: String,
    val email: String
)
// FIN DEL CÓDIGO
