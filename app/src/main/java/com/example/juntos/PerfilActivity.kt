package com.example.juntos   // <- verifica que coincida con tu package

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
/**
 * 🚀 PerfilActivity
 * Esta pantalla muestra un formulario con datos del usuario.
 * - Nombre
 * - Correo
 * - Teléfono
 * - Dirección
 * - Fecha de nacimiento
 *
 * Al presionar "Guardar", por ahora solo muestra un Toast simulando que se guardó.
 * (en el futuro podrías conectar con base de datos o API).
 */
class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil) // debe apuntar a tu layout de perfil

        // -----------------------------------------------------------------
        // 🔹 REFERENCIAS A LOS INPUTS DEL LAYOUT
        // Aquí conectamos las variables de Kotlin con los EditText y el botón
        // definidos en el XML (activity_perfil.xml).
        // -----------------------------------------------------------------
        val inputNombre = findViewById<EditText>(R.id.inputNombre)
        val inputCorreo = findViewById<EditText>(R.id.inputCorreo)
        val inputTelefono = findViewById<EditText>(R.id.inputTelefono)
        val inputDireccion = findViewById<EditText>(R.id.inputDireccion)
        val inputFecha = findViewById<EditText>(R.id.inputFechaNacimiento)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        // -----------------------------------------------------------------
        // ✅ ACCIÓN DEL BOTÓN GUARDAR
        // Cuando el usuario presiona "Guardar":
        // - Toma el texto del campo nombre
        // - Muestra un Toast como demostración
        //
        // 💡 En un caso real, aquí guardarías los datos en base de datos o API
        // -----------------------------------------------------------------
        btnGuardar.setOnClickListener {
            // ejemplo: mostrar toast con el nombre (simula "guardar")
            val nombre = inputNombre.text.toString().trim()
            Toast.makeText(this, "Guardado (demo): $nombre", Toast.LENGTH_SHORT).show()
        }
    }
}

