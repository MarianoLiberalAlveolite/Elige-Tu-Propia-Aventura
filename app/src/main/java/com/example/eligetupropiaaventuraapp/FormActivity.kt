package com.example.eligetupropiaaventuraapp

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Permite que la actividad responda a todas las orientaciones físicas del dispositivo
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        setContentView(R.layout.activity_form2) // Asigna el layout de la actividad

        // Referencias a los controles del layout
        val nombreEditText = findViewById<EditText>(R.id.nombreForm)
        val generoSpinner = findViewById<Spinner>(R.id.tipoFiccion)
        val generoRadioGroup = findViewById<RadioGroup>(R.id.generoForm)
        val dificultadSwitch = findViewById<Switch>(R.id.dificultad)
        val startButton = findViewById<Button>(R.id.startButton)

        // Referencias a los CheckBox de atributos
        val checkboxFuerza = findViewById<CheckBox>(R.id.checkboxFuerza)
        val checkboxDestreza = findViewById<CheckBox>(R.id.checkboxDestreza)
        val checkboxInteligencia = findViewById<CheckBox>(R.id.checkboxInteligencia)
        val checkboxMagia = findViewById<CheckBox>(R.id.checkboxMagia)

        // Configura el Spinner con los géneros de ficción
        configurarSpinner(generoSpinner)

        // Limita la selección de atributos a un máximo de 2
        setupCheckBoxLimit(checkboxFuerza, checkboxDestreza, checkboxInteligencia, checkboxMagia, maxChecked = 2)

        // Guarda el fondo original del EditText para restaurarlo después
        val defaultBackground = nombreEditText.background

        // Cambia el color de fondo del EditText cuando recibe o pierde el foco
        nombreEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Cambia el color de fondo cuando el campo tiene el foco
                nombreEditText.setBackgroundColor(ContextCompat.getColor(this, R.color.orange))
            } else {
                // Restaura el fondo original cuando pierde el foco
                nombreEditText.background = defaultBackground
            }
        }

        // Acción al pulsar el botón de inicio
        startButton.setOnClickListener {
            // Recoge los valores introducidos por el usuario
            val nombre = nombreEditText.text.toString()
            val generoId = generoRadioGroup.checkedRadioButtonId
            val genero = findViewById<RadioButton>(generoId)?.text?.toString() ?: ""
            val tipoFiccion = generoSpinner.selectedItem.toString()
            val dificultad = if (dificultadSwitch.isChecked) "difícil" else "normal"

            // Recoge los atributos seleccionados por el usuario
            val atributosSeleccionados = listOf(
                checkboxFuerza to "Fuerza",
                checkboxDestreza to "Destreza",
                checkboxInteligencia to "Inteligencia",
                checkboxMagia to "Magia"
            ).filter { it.first.isChecked }
                .map { it.second }

            // Muestra un resumen de los datos recogidos en un Toast
            val resumen = "Nombre: $nombre\nGénero: $genero\nFicción: $tipoFiccion\nDificultad: $dificultad\nAtributos: ${atributosSeleccionados.joinToString(", ")}"
            Toast.makeText(this, resumen, Toast.LENGTH_LONG).show()

            // Prepara el prompt con los datos recogidos localizado
            val prompt = getString(
                R.string.prompt_aventura,
                nombre,
                genero,
                tipoFiccion,
                dificultad,
                atributosSeleccionados.joinToString(", ")
            )

            // Llama a la función para generar la historia y mostrarla en la siguiente pantalla
            generarHistoriaYMostrar(prompt)
        }
    }

    // Configura el Spinner con los géneros de ficción y personaliza los colores de los textos
    private fun configurarSpinner(spinner: Spinner) {
        val generos = arrayOf("Fantasía", "Ciencia Ficción", "Terror", "Aventura")

        val adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            generos
        ) {
            // Color del texto cuando el Spinner no está desplegado
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                (view as TextView).setTextColor(Color.WHITE)
                return view
            }

            // Color del texto cuando el Spinner está desplegado
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                (view as TextView).setTextColor(Color.BLACK)
                return view
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0, false) // Selecciona el primer elemento por defecto
    }

    // Limita el número de CheckBox seleccionados a un máximo de 'maxChecked'
    private fun setupCheckBoxLimit(vararg checkBoxes: CheckBox, maxChecked: Int = 2) {
        val listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val checkedCount = checkBoxes.count { it.isChecked }
            if (checkedCount > maxChecked) {
                // Si se supera el máximo, desmarca el último que intentó marcar y muestra un mensaje
                buttonView.isChecked = false
                Toast.makeText(this, "Solo puedes elegir $maxChecked atributos.", Toast.LENGTH_SHORT).show()
            }
        }
        // Asigna el listener a todos los CheckBox
        checkBoxes.forEach { it.setOnCheckedChangeListener(listener) }
    }

    // Llama a la API de IA para generar una historia y la muestra en la siguiente pantalla
    private fun generarHistoriaYMostrar(prompt: String) {
        ConsultaGemini.generarHistoria(
            prompt = prompt,
            onResultado = { historiaGenerada ->
                // Crea el intent y pasa la historia generada a ResultActivity
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("historia_generada", historiaGenerada)
                startActivity(intent)
            },
            onError = { mensajeError ->
                // Muestra un mensaje de error en el hilo principal
                runOnUiThread {
                    Toast.makeText(this, mensajeError, Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}
