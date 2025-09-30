package com.example.juntos

import android.app.TimePickerDialog
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
//import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.setMargins
import com.google.android.material.floatingactionbutton.FloatingActionButton
//import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.min
/**
 * 🚀 DayScheduleActivity
 * Esta actividad implementa una agenda diaria tipo "Google Calendar".
 * Características:
 * - Muestra las 24 horas del día en filas.
 * - Cada fila tiene: una etiqueta con la hora y un espacio para tareas/eventos.
 * - Un botón flotante (+) abre un diálogo para añadir tareas.
 * - Permite asignar título, horas de inicio/fin, categoría y compañero.
 * - Pinta el evento en su franja horaria con color según la categoría.
 */
class DayScheduleActivity : AppCompatActivity() {
    // -----------------------------------------------------------------
    // 🔹 VARIABLES PRINCIPALES
    // containerHours -> contenedor de todas las filas de 24 horas
    // fabAdd -> botón flotante para añadir nueva tarea
    // categories -> categorías de eventos que el usuario puede elegir
    // hourSlots -> lista de contenedores para insertar eventos
    // -----------------------------------------------------------------
    private lateinit var containerHours: LinearLayout
    private lateinit var fabAdd: FloatingActionButton

    private val categories = mutableListOf("Trabajo", "Ocio", "Personal", "Deporte")
    private val hourSlots = mutableListOf<FrameLayout>()

    // altura en dp que tendrá cada hora (ajusta si quieres más/menos)
    private val hourHeightDp = 120

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tareas)

        containerHours = findViewById(R.id.containerHours)
        fabAdd = findViewById(R.id.fabAdd)
        // Crea las 24 filas (0h a 23h)
        createHourRows()
        // Acción al presionar el botón flotante
        fabAdd.setOnClickListener { showCreateTaskDialog() }
    }
    // -----------------------------------------------------------------
    // ✅ CREAR FILAS DE HORAS
    // Este método genera 24 filas, cada una con:
    // - Una etiqueta de hora (ej: "9 a. m.")
    // - Un contenedor FrameLayout para eventos de esa hora
    // -----------------------------------------------------------------
    private fun createHourRows() {
        val density = resources.displayMetrics.density
        val hourHeightPx = (hourHeightDp * density).toInt()

        for (hour in 0..23) {
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    hourHeightPx
                )
                layoutParams = lp
            }

            // label (hora) - fijo a la izquierda
            val label = TextView(this).apply {
                val w = (72 * density).toInt()
                layoutParams = LinearLayout.LayoutParams(w, LinearLayout.LayoutParams.MATCH_PARENT)
                text = formatHourLabel(hour)
                setPadding(8, 8, 8, 8)
            }

            // slot para eventos (FrameLayout) a la derecha
            val slot = FrameLayout(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1f
                )
                // darle fondo sutil para ver separación si quieres
                // setBackgroundColor(Color.parseColor("#ffffff"))
                tag = "slot_$hour"
            }

            row.addView(label)
            row.addView(slot)

            containerHours.addView(row)
            hourSlots.add(slot)
        }
    }
    // 🔹 Formatea la etiqueta de hora (ej: 13 -> "1 p. m.")
    private fun formatHourLabel(hour: Int): String {
        // formato tipo "9 a. m." / "1 p. m."
        val h = when {
            hour == 0 -> 12
            hour <= 12 -> hour
            else -> hour - 12
        }
        val ampm = if (hour < 12) "a. m." else "p. m."
        return "$h $ampm"
    }
    // -----------------------------------------------------------------
// ✅ DIÁLOGO PARA CREAR TAREA
// Muestra un formulario emergente (AlertDialog) con estos campos:
// - Título de la tarea
// - Hora inicio y hora fin (seleccionados con un TimePicker)
// - Categoría (con Spinner, permite añadir nuevas categorías)
// - Compañero (texto opcional)
// Al guardar: valida datos y crea el evento en el horario.
// -----------------------------------------------------------------
    private fun showCreateTaskDialog() {
        // -----------------------------------------------------------------
        // 📌 Inflamos el layout personalizado del diálogo (dialog_tarea.xml)
        // -----------------------------------------------------------------
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_tarea, null)
        // Referencias a los elementos del formulario
        val edtTitle = dialogView.findViewById<EditText>(R.id.edtTitle)
        val edtInicio = dialogView.findViewById<EditText>(R.id.edtHoraInicio)
        val edtFin = dialogView.findViewById<EditText>(R.id.edtHoraFin)
        val spinner = dialogView.findViewById<Spinner>(R.id.spinnerCategoria)
        val btnAddCat = dialogView.findViewById<Button>(R.id.btnAddCategoria)
        val edtComp = dialogView.findViewById<EditText>(R.id.edtCompanero)
        val btnGuardar = dialogView.findViewById<Button>(R.id.btnGuardarDialog)
        // -----------------------------------------------------------------
        // 🎛️ Configurar el spinner de categorías
        // Se alimenta con la lista "categories" definida en la actividad
        // -----------------------------------------------------------------
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        spinner.adapter = adapter

        // -----------------------------------------------------------------
        // ⏰ Selección de horas con TimePickerDialog
        // Cuando el usuario toca el campo de hora, se abre un reloj
        // -----------------------------------------------------------------
        edtInicio.setOnClickListener {
            showTimePicker { h, m -> edtInicio.setText(String.format("%02d:%02d", h, m)) }
        }
        edtFin.setOnClickListener {
            showTimePicker { h, m -> edtFin.setText(String.format("%02d:%02d", h, m)) }
        }

        // -----------------------------------------------------------------
        // ➕ Botón para añadir nuevas categorías
        // Se abre un mini AlertDialog con un EditText
        // Si el usuario escribe algo, se agrega a la lista de categorías
        // -----------------------------------------------------------------
        btnAddCat.setOnClickListener {
            val input = EditText(this)
            AlertDialog.Builder(this)
                .setTitle("Nueva categoría")
                .setView(input)
                .setPositiveButton("Agregar") { _, _ ->
                    val cat = input.text.toString().trim()
                    if (!TextUtils.isEmpty(cat)) {
                        categories.add(cat)
                        adapter.notifyDataSetChanged()
                        spinner.setSelection(categories.size - 1)
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
        // -----------------------------------------------------------------
        // 📦 Crear el diálogo con la vista personalizada
        // -----------------------------------------------------------------
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()
        // -----------------------------------------------------------------
        // 💾 Botón "Guardar"
        // Valida los datos y llama a addEventToSchedule
        // -----------------------------------------------------------------
        btnGuardar.setOnClickListener {
            val title = if (edtTitle.text.toString().trim().isEmpty()) "Evento" else edtTitle.text.toString().trim()
            val start = edtInicio.text.toString().trim()
            val end = edtFin.text.toString().trim()
            val cat = spinner.selectedItem.toString()
            val comp = edtComp.text.toString().trim()
            // Validar que haya horas de inicio y fin
            if (start.isEmpty() || end.isEmpty()) {
                Toast.makeText(this, "Selecciona hora inicio y fin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Convertir horas a minutos totales
            val startMinutes = parseTimeToMinutes(start)
            val endMinutes = parseTimeToMinutes(end)
            // Validar que fin > inicio
            if (endMinutes <= startMinutes) {
                Toast.makeText(this, "La hora fin debe ser posterior a inicio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 🚀 Si todo está bien, se añade al horario
            addEventToSchedule(title, startMinutes, endMinutes, cat, comp)
            // Cierra el diálogo
            dialog.dismiss()
        }
        // -----------------------------------------------------------------
        // 👀 Mostrar el diálogo en pantalla
        // -----------------------------------------------------------------
        dialog.show()
    }

    // 🔹 Picker de hora
    private fun showTimePicker(onPicked: (hour: Int, minute: Int) -> Unit) {
        val now = java.util.Calendar.getInstance()
        val h = now.get(java.util.Calendar.HOUR_OF_DAY)
        val m = now.get(java.util.Calendar.MINUTE)
        val picker = TimePickerDialog(this, { _, hourOfDay, minute ->
            onPicked(hourOfDay, minute)
        }, h, m, true)
        picker.show()
    }
    // 🔹 Convierte "HH:mm" a minutos totales
    private fun parseTimeToMinutes(hhmm: String): Int {
        val parts = hhmm.split(":")
        val hh = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val mm = parts.getOrNull(1)?.toIntOrNull() ?: 0
        return hh * 60 + mm
    }
    // -----------------------------------------------------------------
// ✅ AGREGAR EVENTO AL HORARIO
// Esta función recibe:
// - title → título del evento (ejemplo: "Reunión")
// - startMin → hora de inicio en minutos desde las 00:00 (ej: 9:30 → 570)
// - endMin → hora de fin en minutos (ej: 10:15 → 615)
// - category → tipo de evento (Trabajo, Ocio, etc.)
// - companion → compañero asociado (ej: "Carlos")
//
// Lo que hace es:
// 1. Calcular en qué fila/hora debe ir el evento.
// 2. Dibujarlo como un bloque de color en el horario.
// 3. Ajustar su altura proporcional a la duración.
// 4. Dividirlo en varias filas si ocupa más de una hora.
// -----------------------------------------------------------------
    private fun addEventToSchedule(title: String, startMin: Int, endMin: Int, category: String, companion: String) {
        // Conversión de DP a píxeles según la densidad de la pantalla
        val density = resources.displayMetrics.density
        val hourHeightPx = (hourHeightDp * density).toInt()// altura total de cada fila/hora
        val pixelsPerMinute = hourHeightPx / 60f // cuántos píxeles equivale un minuto
        // Calculamos cuántos minutos dura el evento
        var remaining = endMin - startMin
        var cursor = startMin

        // -----------------------------------------------------------------
        // 🎨 Seleccionar color del evento según la categoría
        // -----------------------------------------------------------------
        val color = when (category.lowercase()) {
            "trabajo" -> 0xFF1976D2.toInt() // blue
            "ocio" -> 0xFF43A047.toInt() // green
            "personal" -> 0xFF8E24AA.toInt() // purple
            "deporte" -> 0xFFF57C00.toInt() // orange
            else -> 0xFF0B5FFF.toInt() // default azul oscuro
        }
        // -----------------------------------------------------------------
        // 🔄 Bucle: divide el evento si ocupa varias horas
        // -----------------------------------------------------------------
        while (remaining > 0) {
            // Índice de la hora en la que cae el "cursor"
            val hourIdx = cursor / 60
            if (hourIdx !in 0..23) break// por seguridad, no salirse del rango del día
            // Minuto dentro de esa hora (ej: 9:15 → offset 15)
            val offsetMin = cursor % 60
            // Cuántos minutos caben dentro de esa hora (ej: si empieza a :45 solo caben 15 min)
            val minutesThisHour = min(60 - offsetMin, remaining)
            // Calcular posición y tamaño en píxeles
            val topMarginPx = (offsetMin * pixelsPerMinute).toInt()// desde qué altura empieza
            val heightPx = (minutesThisHour * pixelsPerMinute).toInt() // altura según duración

            // -----------------------------------------------------------------
            // 📌 Crear la vista visual del evento (un TextView como bloque de color)
            // -----------------------------------------------------------------
            val eventView = TextView(this).apply {
                // Texto: si hay compañero, se concatena al título
                text = if (companion.isNotEmpty())
                    "$title · $companion"
                else title
                setTextColor(0xFFFFFFFF.toInt())// texto blanco
                setPadding((8 * density).toInt(), (6 * density).toInt(), (8 * density).toInt(), (6 * density).toInt())

                maxLines = 2
                ellipsize = android.text.TextUtils.TruncateAt.END
                // Fondo redondeado con color de la categoría
                background = GradientDrawable().apply {
                    cornerRadius = 12f * density
                    setColor(color)
                }
            }
            // -----------------------------------------------------------------
            // 📐 Posicionar el bloque dentro de la celda/hora
            // -----------------------------------------------------------------
            val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightPx)
            params.topMargin = topMarginPx// se coloca a partir del minuto dentro de la hora
            val sideMargin = (4 * density).toInt()
            params.leftMargin = sideMargin
            params.rightMargin = sideMargin
            // Insertar el bloque dentro del slot de esa hora
            hourSlots[hourIdx].addView(eventView, params)
            // Avanzar el cursor y descontar minutos restantes
            remaining -= minutesThisHour
            cursor += minutesThisHour
        }
    }
}

