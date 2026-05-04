package com.example.chaquiapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaquiapp.data.model.Incident
import com.example.chaquiapp.viewmodel.IncidentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewIncidentScreen(
    viewModel: IncidentViewModel,
    navController: NavController
) {
    var type by remember { mutableStateOf("Hueco") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var urgency by remember { mutableStateOf("Media") }

    val types = listOf("Hueco", "Semáforo", "Derrumbe", "Inundación")
    val urgencies = listOf("Baja", "Media", "Alta")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Reporte") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tipo de Incidente
            Text("Tipo de Incidente", style = MaterialTheme.typography.titleMedium)
            types.forEach { t ->
                Row {
                    RadioButton(
                        selected = type == t,
                        onClick = { type = t }
                    )
                    Text(t, modifier = Modifier.align(Alignment.CenterVertically))
                }
            }

            // Descripción
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Ubicación
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Ubicación (calle / referencia)") },
                modifier = Modifier.fillMaxWidth()
            )

            // Urgencia
            Text("Nivel de Urgencia", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                urgencies.forEach { u ->
                    FilterChip(
                        selected = urgency == u,
                        onClick = { urgency = u },
                        label = { Text(u) }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (description.isNotBlank() && location.isNotBlank()) {
                        val incident = Incident(
                            type = type,
                            description = description,
                            location = location,
                            urgency = urgency
                        )
                        viewModel.addIncident(incident)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Reporte")
            }
        }
    }
}