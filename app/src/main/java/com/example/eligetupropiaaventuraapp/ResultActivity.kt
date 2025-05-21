package com.example.eligetupropiaaventuraapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {

    val botonVolver = findViewById<Button>(R.id.btnVolver)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result3)

        val botonVolver = findViewById<Button>(R.id.btnVolver)

        // boton que regresa a la pantalla anterior
        botonVolver.setOnClickListener {
            finish();
        }

    }


}