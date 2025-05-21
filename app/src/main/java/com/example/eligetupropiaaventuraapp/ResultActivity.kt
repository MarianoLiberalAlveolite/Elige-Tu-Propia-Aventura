package com.example.eligetupropiaaventuraapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result3)

        // Permite todas las orientaciones físicas del dispositivo
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR)
        setContentView(R.layout.activity_result3) // Usa tu layout correcto

        // Recoge el dato enviado desde la otra Activity
        val historiaGenerada = intent.getStringExtra("historia_generada")

        // Busca el TextView donde mostrarás la historia
        val textViewHistoria = findViewById<TextView>(R.id.historia)
        textViewHistoria.text = historiaGenerada

        val botonVolver = findViewById<Button>(R.id.btnVolver)

        // boton que regresa a la pantalla anterior
        botonVolver.setOnClickListener {
            finish();
        }

    }


}