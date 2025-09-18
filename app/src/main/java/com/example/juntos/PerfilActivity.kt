package com.example.juntos

import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil) // este XML lo vamos a crear ahora

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, day ->
            val fecha = "$day/${month + 1}/$year"
            Toast.makeText(this, "Seleccionaste: $fecha", Toast.LENGTH_SHORT).show()
        }
    }
}
