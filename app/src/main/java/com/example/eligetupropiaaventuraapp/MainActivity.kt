package com.example.eligetupropiaaventuraapp

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs



class MainActivity : AppCompatActivity() {

    private var toastMostrado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //SCREEN_ORIENTATION_FULL_SENSOR: La actividad responde a todas las orientaciones posibles del dispositivo, usando el sensor.
        //setRequestedOrientation(...): Fija la orientación preferida de la actividad en tiempo de ejecución.
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        setContentView(R.layout.activity_main1)

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
        
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR)
    }

}