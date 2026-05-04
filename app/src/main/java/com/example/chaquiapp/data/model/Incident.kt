package com.example.chaquiapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incidents")
data class Incident(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val description: String,
    val location: String,
    val urgency: String,        // Baja, Media, Alta
    val status: String = "Reportado", // Reportado, En atención, Resuelto
    val date: Long = System.currentTimeMillis()
)