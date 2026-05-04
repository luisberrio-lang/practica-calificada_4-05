package com.example.chaquiapp

import android.app.Application
import com.example.chaquiapp.data.local.IncidentDatabase

class ChaquiApplication : Application() {

    val database: IncidentDatabase by lazy {
        IncidentDatabase.getDatabase(this)
    }
}