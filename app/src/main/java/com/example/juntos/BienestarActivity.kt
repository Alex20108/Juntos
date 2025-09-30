package com.example.juntos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
/**
 * 🚀 BienestarActivity
 * Pantalla sencilla que muestra frases motivacionales al usuario.
 * Características:
 * - Tiene un TextView para mostrar una frase.
 * - Tiene un botón "Nueva frase" que al presionar cambia el texto.
 */
class BienestarActivity : AppCompatActivity() {

    // Índice para saber qué frase mostrar
    private val frases = listOf(
        "Respira profundo y sigue adelante 🌿",
        "Hoy es un buen día para sonreír 😊",
        "Confía en ti, lo estás haciendo bien 💪",
        "Rodéate de lo que te haga feliz ❤️",
        "Cada pequeño paso cuenta 🚀"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienestar)// 🔹 Layout asociado a esta pantalla

    }
}
