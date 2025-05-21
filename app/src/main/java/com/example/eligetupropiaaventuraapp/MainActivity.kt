package com.example.eligetupropiaaventuraapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var gestorGiroscopio: GestorGiroscopio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main1)

        gestorGiroscopio = GestorGiroscopio(this) { x, y, z -> }

        if (!gestorGiroscopio.tieneGiroscopio()) {
            Toast.makeText(this, "Este dispositivo no tiene giroscopio", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val boton = findViewById<ImageButton>(R.id.btnGoToForm)
        boton.setOnClickListener{
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        gestorGiroscopio.iniciar()
    }

    override fun onPause() {
        super.onPause()
        gestorGiroscopio.detener()
    }

}