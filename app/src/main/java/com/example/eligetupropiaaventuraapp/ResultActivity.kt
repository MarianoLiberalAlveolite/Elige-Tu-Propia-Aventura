package com.example.eligetupropiaaventuraapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        setContentView(R.layout.activity_result3) // Asegúrate de tener el layout correcto

        // Recoge el dato enviado desde la otra Activity
        val historiaGenerada = intent.getStringExtra("historia_generada")

        // Busca el TextView donde mostrarás la historia
        val textViewHistoria = findViewById<TextView>(R.id.historia)

        // Muestra el texto en el TextView
        textViewHistoria.text = historiaGenerada
    }
}