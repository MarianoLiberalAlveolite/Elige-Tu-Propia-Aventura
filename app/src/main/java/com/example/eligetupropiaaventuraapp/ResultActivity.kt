package com.example.eligetupropiaaventuraapp

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Permite todas las orientaciones físicas del dispositivo
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR)
        setContentView(R.layout.activity_result3) // Usa tu layout correcto

        // Recoge el dato enviado desde la otra Activity
        val historiaGenerada = intent.getStringExtra("historia_generada")

        // Busca el TextView donde mostrarás la historia
        val textViewHistoria = findViewById<TextView>(R.id.historia)
        textViewHistoria.text = historiaGenerada

        // Botón para volver al formulario
        val botonVolver = findViewById<Button>(R.id.botonVolver)
        botonVolver.setOnClickListener {
            // volver explícitamente al formulario (por si quieres reiniciar el formulario)
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
