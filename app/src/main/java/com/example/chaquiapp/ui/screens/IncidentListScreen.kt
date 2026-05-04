package com.example.chaquiapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaquiapp.data.model.Incident
import com.example.chaquiapp.ui.navigation.Screen
import com.example.chaquiapp.viewmodel.IncidentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentListScreen(
    viewModel: IncidentViewModel,
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ChaquiApp") },
                subtitle = { Text("Reportes Vecinales") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.New.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo reporte")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.value.incidents.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(uiState.value.incidents) { incident ->
                        IncidentCard(
                            incident = incident,
                            onClick = {
                                navController.navigate("detail/${incident.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IncidentCard(incident: Incident, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = incident.type,
                    style = MaterialTheme.typography.titleMedium
                )
                UrgencyChip(incident.urgency)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = incident.description, maxLines = 2)
            Text(
                text = incident.location,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            StatusChip(incident.status)
        }
    }
}

@Composable
fun UrgencyChip(urgency: String) {
    val color = when (urgency) {
        "Alta" -> MaterialTheme.colorScheme.error
        "Media" -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.primary
    }
    AssistChip(
        onClick = {},
        label = { Text(urgency) },
        colors = AssistChipDefaults.assistChipColors(containerColor = color.copy(alpha = 0.2f))
    )
}

@Composable
fun StatusChip(status: String) {
    val color = when (status) {
        "Resuelto" -> MaterialTheme.colorScheme.primary
        "En atención" -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.secondary
    }
    AssistChip(
        onClick = {},
        label = { Text(status) },
        colors = AssistChipDefaults.assistChipColors(containerColor = color.copy(alpha = 0.2f))
    )
}

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Aún no hay reportes", style = MaterialTheme.typography.headlineSmall)
            Text("¡Sé el primero en reportar!", style = MaterialTheme.typography.bodyLarge)
        }
    }
}