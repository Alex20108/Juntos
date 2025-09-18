package com.example.juntos

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import android.widget.Toast

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

        iconHome.setOnClickListener {
            Toast.makeText(this, "Ya estás en Inicio 🏠", Toast.LENGTH_SHORT).show()
        }

        //iconPerfil.setOnClickListener {
         //   startActivity(Intent(this, PerfilActivity::class.java))
        //}

        // ✅ Acciones de la barra superior
        imgLogo.setOnClickListener {
            Toast.makeText(this, "Logo de la app 😊", Toast.LENGTH_SHORT).show()
        }

        //imgPerfil.setOnClickListener {
         //   startActivity(Intent(this, PerfilActivity::class.java))
       // }

        // ✅ Acción del calendario
        calendarView.setOnDateChangedListener { _, date, _ ->
            val fechaSeleccionada = "${date.day}/${date.month + 1}/${date.year}"
            Toast.makeText(this, "Seleccionaste: $fechaSeleccionada", Toast.LENGTH_SHORT).show()

            // Ejemplo: abrir actividad de tareas con la fecha seleccionada
            val intent = Intent(this, TareasActivity::class.java)
            intent.putExtra("fecha", fechaSeleccionada)
            startActivity(intent)
        }
    }
}
