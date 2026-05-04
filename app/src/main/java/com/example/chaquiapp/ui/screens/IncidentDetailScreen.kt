package com.example.chaquiapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaquiapp.viewmodel.IncidentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentDetailScreen(
    incidentId: Int,
    viewModel: IncidentViewModel,
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsState()
    val incident = uiState.value.incidents.find { it.id == incidentId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Reporte") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        incident?.let { inc ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(inc.type, style = MaterialTheme.typography.headlineMedium)
                UrgencyChip(inc.urgency)
                StatusChip(inc.status)

                Text("Descripción:", style = MaterialTheme.typography.titleMedium)
                Text(inc.description)

                Text("Ubicación:", style = MaterialTheme.typography.titleMedium)
                Text(inc.location)

                Text("Fecha: ${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(java.util.Date(inc.date))}")

                Spacer(modifier = Modifier.weight(1f))

                // Botones para cambiar estado
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { viewModel.updateStatus(inc, "En atención") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("En Atención")
                    }
                    Button(
                        onClick = { viewModel.updateStatus(inc, "Resuelto") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Marcar como Resuelto")
                    }
                }
            }
        } ?: Text("Reporte no encontrado")
    }
}