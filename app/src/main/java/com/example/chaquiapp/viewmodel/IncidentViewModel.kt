package com.example.chaquiapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chaquiapp.data.local.IncidentDatabase
import com.example.chaquiapp.data.model.Incident
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IncidentViewModel(private val database: IncidentDatabase) : ViewModel() {

    private val dao = database.incidentDao()

    private val _uiState = MutableStateFlow(IncidentUiState())
    val uiState: StateFlow<IncidentUiState> = _uiState.asStateFlow()

    init {
        loadIncidents()
    }

    private fun loadIncidents() {
        viewModelScope.launch {
            dao.getAllIncidents().collect { incidents ->
                _uiState.value = _uiState.value.copy(incidents = incidents)
            }
        }
    }

    fun addIncident(incident: Incident) {
        viewModelScope.launch {
            dao.insertIncident(incident)
        }
    }

    fun updateStatus(incident: Incident, newStatus: String) {
        viewModelScope.launch {
            val updated = incident.copy(status = newStatus)
            dao.updateIncident(updated)
        }
    }

    fun deleteIncident(id: Int) {
        viewModelScope.launch {
            dao.deleteIncident(id)
        }
    }
}

data class IncidentUiState(
    val incidents: List<Incident> = emptyList()
)