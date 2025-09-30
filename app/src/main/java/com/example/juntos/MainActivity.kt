package com.example.juntos

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import android.widget.Toast

/**
 * 🚀 MainActivity
 * Esta es la pantalla principal de la app "Juntos".
 * Contiene:
 * - Una barra superior (logo + foto perfil)
 * - Un calendario interactivo
 * - Una barra inferior de navegación (Bienestar, Inicio, Perfil)
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 📅 Referencia al calendario
        val calendarView = findViewById<MaterialCalendarView>(R.id.materialCalendarView)

        // 👉 Barra inferior
        val iconBienestar = findViewById<ImageView>(R.id.iconBienestar)
        val iconHome = findViewById<ImageView>(R.id.iconHome)
        val iconPerfil = findViewById<ImageView>(R.id.iconPerfil)

        // 👉 Barra superior
        val imgLogo = findViewById<ImageView>(R.id.imgLogo)
        val imgPerfil = findViewById<ImageView>(R.id.imgPerfil)

        // ✅ Acciones de los botones inferiores
        iconBienestar.setOnClickListener {
            startActivity(Intent(this, BienestarActivity::class.java))
        }
        // 🔹 Inicio (ya estamos aquí, solo muestra un mensaje)
        iconHome.setOnClickListener {
            Toast.makeText(this, "Ya estás en Inicio 🏠", Toast.LENGTH_SHORT).show()
        }
        //🔹 Ir al Perfil (DESACTIVADO por ahora, quita los // para activarlo)
        iconPerfil.setOnClickListener {
           startActivity(Intent(this, PerfilActivity::class.java))
        }

        // ✅ Acciones de la barra superior
        imgLogo.setOnClickListener {
            Toast.makeText(this, "Logo de la app 😊", Toast.LENGTH_SHORT).show()
        }
        // 🔹 Foto de perfil (DESACTIVADO por ahora)
        imgPerfil.setOnClickListener {
           startActivity(Intent(this, PerfilActivity::class.java))
        }

        // -----------------------------------------------------------------
        // ✅ ACCIÓN DEL CALENDARIO
        // Cuando el usuario selecciona una fecha:
        // - Muestra un mensaje con la fecha elegida
        // - Abre la pantalla de Tareas (DayScheduleActivity) pasando la fecha
        // -----------------------------------------------------------------
        calendarView.setOnDateChangedListener { _, date, _ ->
            val fechaSeleccionada = "${date.day}/${date.month + 1}/${date.year}"
            Toast.makeText(this, "Seleccionaste: $fechaSeleccionada", Toast.LENGTH_SHORT).show()

            // 🔹 Abrir pantalla de tareas y enviar la fecha seleccionada
            val intent = Intent(this, DayScheduleActivity::class.java)
            intent.putExtra("fecha", fechaSeleccionada)
            startActivity(intent)
        }
    }
}
