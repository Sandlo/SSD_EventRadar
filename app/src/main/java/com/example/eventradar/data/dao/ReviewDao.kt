package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.eventradar.data.entities.Review

/**
 * Data Access Object (DAO) Schnittstelle für den Zugriff auf Bewertungsdaten in der Datenbank.
 */
@Dao
interface ReviewDao {
    /**
     * Fügt einen oder mehrere Bewertungsdatensätze in die Datenbank ein.
     */
    @Insert
    suspend fun insertAll(vararg reviews: Review)
}
