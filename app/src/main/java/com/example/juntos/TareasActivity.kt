package com.example.juntos

import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TareasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tareas)

        val chkTarea1 = findViewById<CheckBox>(R.id.chkTarea1)
        val chkTarea2 = findViewById<CheckBox>(R.id.chkTarea2)
        val chkTarea3 = findViewById<CheckBox>(R.id.chkTarea3)
        val chkTarea4 = findViewById<CheckBox>(R.id.chkTarea4)

        val listaTareas = listOf(chkTarea1, chkTarea2, chkTarea3, chkTarea4)

        listaTareas.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Toast.makeText(this, "✔ ${checkBox.text} completada", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "❌ ${checkBox.text} pendiente", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
