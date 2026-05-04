package com.example.chaquiapp.data.local

import androidx.room.*
import com.example.chaquiapp.data.model.Incident
import kotlinx.coroutines.flow.Flow

@Dao
interface IncidentDao {

    @Query("SELECT * FROM incidents ORDER BY " +
            "CASE urgency " +
            "WHEN 'Alta' THEN 1 " +
            "WHEN 'Media' THEN 2 " +
            "ELSE 3 END, date DESC")
    fun getAllIncidents(): Flow<List<Incident>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncident(incident: Incident)

    @Update
    suspend fun updateIncident(incident: Incident)

    @Query("DELETE FROM incidents WHERE id = :id")
    suspend fun deleteIncident(id: Int)
}