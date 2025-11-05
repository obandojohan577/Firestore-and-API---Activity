// INICIO DEL CÓDIGO para RetrofitClient.kt
package com.example.composeapiapp.data

import androidx.privacysandbox.tools.core.generator.build
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // 10.0.2.2 es la IP para conectar al localhost de tu PC desde el emulador de Android
    private const val BASE_URL = "http://10.0.2.2:3000/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}
// FIN DEL CÓDIGO
