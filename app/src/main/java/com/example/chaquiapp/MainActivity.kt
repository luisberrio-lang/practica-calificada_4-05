package com.example.chaquiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chaquiapp.ui.navigation.AppNavigation
import com.example.chaquiapp.ui.theme.ChaquiAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Obtenemos la base de datos desde la Application
        val application = application as ChaquiApplication
        val viewModel = IncidentViewModel(application.database)

        setContent {
            ChaquiAppTheme {
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}