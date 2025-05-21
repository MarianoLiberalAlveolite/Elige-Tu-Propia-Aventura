package com.example.eligetupropiaaventuraapp

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import com.google.gson.*
import java.io.IOException

// Objeto para comunicarse con la API de Gemini y generar historias
object ConsultaGemini {
    // Cliente para hacer peticiones web
    private val client = OkHttpClient()

    // Convertidor de JSON a objetos Kotlin
    private val gson = Gson()

    // Función principal para generar la historia
    fun generarHistoria(prompt: String, onResultado: (String) -> Unit, onError: (String) -> Unit) {
        // URL de la API con la clave de seguridad
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=${BuildConfig.GEMINI_API_KEY}"

        // Preparamos el texto para la IA en formato JSON
        val cuerpoJson = """
            {
              "contents": [
                {
                  "parts": [
                    {
                      "text": "${prompt.replace("\"", "\\\"")}"
                    }
                  ]
                }
              ]
            }
        """.trimIndent()

        // Construimos la petición web tipo POST
        val request = Request.Builder()
            .url(url)
            .post(RequestBody.create("application/json".toMediaTypeOrNull(), cuerpoJson))
            .build()

        // Enviamos la petición de forma asíncrona
        client.newCall(request).enqueue(object : Callback {
            // Si falla la conexión
            override fun onFailure(call: Call, e: IOException) {
                onError("Error de red: ${e.message}")
            }

            // Cuando recibimos respuesta del servidor
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        // Si el servidor reporta un error
                        onError("Error: ${response.code}")
                    } else {
                        // Procesamos la respuesta exitosa
                        val json = response.body?.string()
                        val contenido = gson.fromJson(json, GeminiRespuesta::class.java)

                        // Extraemos el texto generado de la respuesta
                        val textoGenerado = contenido.candidates?.firstOrNull()
                            ?.content?.parts?.firstOrNull()?.text ?: "No se generó historia"

                        onResultado(textoGenerado)
                    }
                }
            }
        })
    }

    // Clases para entender la estructura de la respuesta JSON

    // Contenedor principal de la respuesta
    data class GeminiRespuesta(
        val candidates: List<Candidate>?  // Lista de posibles respuestas
    )

    // Cada opción de respuesta generada
    data class Candidate(
        val content: Content  // Contenido de la respuesta
    )

    // Partes que componen el contenido
    data class Content(
        val parts: List<Part>  // Fragmentos del texto
    )

    // Cada parte del texto generado
    data class Part(
        val text: String  // Texto final de la historia
    )
}
