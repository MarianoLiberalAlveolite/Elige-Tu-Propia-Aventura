package com.example.eligetupropiaaventuraapp

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form2)

        val nombreEditText = findViewById<EditText>(R.id.nombreForm)
        val generoSpinner = findViewById<Spinner>(R.id.tipoFiccion)
        val generoRadioGroup = findViewById<RadioGroup>(R.id.generoForm)
        val dificultadSwitch = findViewById<Switch>(R.id.dificultad)
        val startButton = findViewById<Button>(R.id.startButton)

        // Referencia a los CheckBox de atributos
        val checkboxFuerza = findViewById<CheckBox>(R.id.checkboxFuerza)
        val checkboxDestreza = findViewById<CheckBox>(R.id.checkboxDestreza)
        val checkboxInteligencia = findViewById<CheckBox>(R.id.checkboxInteligencia)
        val checkboxMagia = findViewById<CheckBox>(R.id.checkboxMagia)

        configurarSpinner(generoSpinner)
        setupCheckBoxLimit(checkboxFuerza, checkboxDestreza, checkboxInteligencia, checkboxMagia, maxChecked = 2)

        startButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val generoId = generoRadioGroup.checkedRadioButtonId
            val genero = findViewById<RadioButton>(generoId)?.text?.toString() ?: ""
            val tipoFiccion = generoSpinner.selectedItem.toString()
            val dificultad = if (dificultadSwitch.isChecked) "difícil" else "normal"

            // Recoger atributos seleccionados
            val atributosSeleccionados = listOf(
                checkboxFuerza to "Fuerza",
                checkboxDestreza to "Destreza",
                checkboxInteligencia to "Inteligencia",
                checkboxMagia to "Magia"
            ).filter { it.first.isChecked }
                .map { it.second }

            // Ejemplo de uso: mostrar los datos recogidos
            val toast = Toast.makeText(
                this,
                "Nombre: $nombre\nGénero: $genero\nFicción: $tipoFiccion\nDificultad: $dificultad\nAtributos: ${atributosSeleccionados.joinToString(", ")}",
                Toast.LENGTH_LONG
            )
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()

            // Aquí sigue el flujo: validación, generación de prompt, navegación, etc.

            ConsultaGemini.generarHistoria(
                prompt = "Escribe una historia de aventuras protagonizada por un dragón amable.",
                onResultado = { historiaGenerada ->
                    // Aqui el String generado se lo ponemos al intent
                },
                onError = { mensajeError ->
                    Toast.makeText(this, mensajeError, Toast.LENGTH_LONG).show()
                }
            )

        }
    }

    private fun configurarSpinner(spinner: Spinner) {
        val generos = arrayOf("Fantasía", "Ciencia Ficción", "Terror", "Aventura")

        val adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            generos
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                (view as TextView).setTextColor(Color.WHITE) // Texto cuando NO está desplegado
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                (view as TextView).setTextColor(Color.BLACK) // Texto del desplegable
                return view
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0, false)
    }

    // Limita la selección de CheckBox a un máximo de 'maxChecked'
    private fun setupCheckBoxLimit(vararg checkBoxes: CheckBox, maxChecked: Int = 2) {
        val listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val checkedCount = checkBoxes.count { it.isChecked }
            if (checkedCount > maxChecked) {
                // Si se supera el máximo, desmarcamos el último que intentó marcar
                buttonView.isChecked = false
                val toast = Toast.makeText(this, "Solo puedes elegir $maxChecked atributos.", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 200) // 200 píxeles desde arriba
                toast.show()
            }
        }
        checkBoxes.forEach { it.setOnCheckedChangeListener(listener) }
    }
}
