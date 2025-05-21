package com.example.eligetupropiaaventuraapp

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import com.google.gson.*
import java.io.IOException

object ConsultaGemini {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun generarHistoria(prompt: String, onResultado: (String) -> Unit, onError: (String) -> Unit) {
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=${BuildConfig.GEMINI_API_KEY}"

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

        val request = Request.Builder()
            .url(url)
            .post(RequestBody.create("application/json".toMediaTypeOrNull(), cuerpoJson))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError("Error de red: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        onError("Error: ${response.code}")
                    } else {
                        val json = response.body?.string()
                        val contenido = gson.fromJson(json, GeminiRespuesta::class.java)
                        val textoGenerado = contenido.candidates?.firstOrNull()
                            ?.content?.parts?.firstOrNull()?.text ?: "No se generó historia"
                        onResultado(textoGenerado)
                    }
                }
            }
        })
    }

    // Clases para mapear la respuesta
    data class GeminiRespuesta(
        val candidates: List<Candidate>?
    )

    data class Candidate(
        val content: Content
    )

    data class Content(
        val parts: List<Part>
    )

    data class Part(
        val text: String
    )
}
