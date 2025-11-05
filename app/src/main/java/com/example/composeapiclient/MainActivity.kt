// INICIO DEL CÓDIGO para MainActivity.kt
package com.example.composeapiapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composeapiapp.data.RetrofitClient
import com.example.composeapiapp.data.User
import com.example.composeapiapp.ui.theme.ComposeAPIClientTheme // Asegúrate que el nombre del tema sea el correcto
import com.example.composeapiclient.ui.theme.ComposeAPIClientTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAPIClientTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserScreen()
                }
            }
        }
    }
}

@Composable
fun UserScreen() {
    // Estado para almacenar la lista de usuarios
    var userList by remember { mutableStateOf<List<User>>(emptyList()) }
    // Estado para los campos de texto
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    // Estado para mostrar mensajes (éxito/error)
    var message by remember { mutableStateOf("") }
    // Estado de carga
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // Función para obtener los usuarios de la API
    fun fetchUsers() {
        isLoading = true
        coroutineScope.launch {
            try {
                val response = RetrofitClient.instance.getUsers()
                if (response.isSuccessful) {
                    userList = response.body() ?: emptyList()
                    message = "Usuarios cargados"
                } else {
                    message = "Error al cargar usuarios: ${response.message()}"
                }
            } catch (e: Exception) {
                message = "Excepción: ${e.message}"
                Log.e("UserScreen", "Error al obtener usuarios", e)
            } finally {
                isLoading = false
            }
        }
    }

    // Función para crear un nuevo usuario
    fun createUser() {
        isLoading = true
        coroutineScope.launch {
            try {
                val newUser = User(name = name, email = email)
                val response = RetrofitClient.instance.createUser(newUser)
                if (response.isSuccessful) {
                    message = "Usuario creado con éxito"
                    // Limpiar campos y refrescar la lista
                    name = ""
                    email = ""
                    fetchUsers()
                } else {
                    message = "Error al crear usuario: ${response.message()}"
                }
            } catch (e: Exception) {
                message = "Excepción: ${e.message}"
                Log.e("UserScreen", "Error al crear usuario", e)
            } finally {
                isLoading = false
            }
        }
    }

    // Cargar los usuarios la primera vez que se muestra la pantalla
    LaunchedEffect(Unit) {
        fetchUsers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Gestión de Usuarios", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Formulario para crear usuario
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { createUser() }, enabled = !isLoading) {
            Text("Crear Usuario")
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (isLoading) {
            CircularProgressIndicator()
        }
        Text(message, style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        // Lista de usuarios
        Text("Lista de Usuarios", style = MaterialTheme.typography.headlineSmall)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(userList) { user ->
                UserItem(user)
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ID: ${user.id ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
            Text("Nombre: ${user.name}", style = MaterialTheme.typography.bodyMedium)
            Text("Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// FIN DEL CÓDIGO
