package com.example.eligetupropiaaventuraapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class GestorGiroscopio(
    contexto: Context,
    private val alCambiarGiroscopio: (x: Float, y: Float, z: Float) -> Unit
) : SensorEventListener {

    private val gestorSensores: SensorManager =
        contexto.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val giroscopio: Sensor? = gestorSensores.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    // Iniciar la lectura del giroscopio
    fun iniciar() {
        giroscopio?.let {
            gestorSensores.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // Detener la lectura del giroscopio
    fun detener() {
        gestorSensores.unregisterListener(this)
    }

    // Se llama automáticamente cuando cambian los valores del giroscopio
    override fun onSensorChanged(evento: SensorEvent?) {
        evento?.let {
            val x = it.values[0] // rotación sobre el eje X
            val y = it.values[1] // rotación sobre el eje Y
            val z = it.values[2] // rotación sobre el eje Z

            alCambiarGiroscopio(x, y, z)
        }
    }

    // Se llama si cambia la precisión del sensor (no es necesario implementarlo)
    override fun onAccuracyChanged(sensor: Sensor?, precision: Int) {
        // Opcional
    }

    // Verifica si el dispositivo tiene giroscopio
    fun tieneGiroscopio(): Boolean {
        return giroscopio != null
    }
}
