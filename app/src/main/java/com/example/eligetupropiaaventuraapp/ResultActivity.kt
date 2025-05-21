package com.example.eligetupropiaaventuraapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
<<<<<<< Updated upstream
=======
    private lateinit var gestorGiroscopio: GestorGiroscopio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result3)

        gestorGiroscopio = GestorGiroscopio(this) { x, y, z -> }
    }
>>>>>>> Stashed changes
}