package com.example.juntos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los botones
        val btnCalendario = findViewById<Button>(R.id.btnCalendario)
        val btnTareas = findViewById<Button>(R.id.btnTareas)
        val btnBienestar = findViewById<Button>(R.id.btnBienestar)

        // Abrir pantalla de calendario 📅
        btnCalendario.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }

        // Abrir pantalla de tareas ✅
        btnTareas.setOnClickListener {
            startActivity(Intent(this, TareasActivity::class.java))
        }

        // Abrir pantalla de bienestar ❤️
        btnBienestar.setOnClickListener {
            startActivity(Intent(this, BienestarActivity::class.java))
        }
    }
}
