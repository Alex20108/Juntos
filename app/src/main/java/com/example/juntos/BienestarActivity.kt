package com.example.juntos

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BienestarActivity : AppCompatActivity() {

    private val frases = listOf(
        "Respira profundo y sigue adelante 🌿",
        "Hoy es un buen día para sonreír 😊",
        "Confía en ti, lo estás haciendo bien 💪",
        "Rodéate de lo que te haga feliz ❤️",
        "Cada pequeño paso cuenta 🚀"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienestar)

        val txtFrase = findViewById<TextView>(R.id.txtFrase)
        val btnNuevaFrase = findViewById<Button>(R.id.btnNuevaFrase)

        btnNuevaFrase.setOnClickListener {
            // Elegir una frase aleatoria
            val fraseAleatoria = frases.random()
            txtFrase.text = fraseAleatoria
        }
    }
}
