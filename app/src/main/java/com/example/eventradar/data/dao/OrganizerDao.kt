package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.eventradar.data.entities.Organizer

/**
 * Data Access Object (DAO) Schnittstelle für den Zugriff auf Veranstalterdaten in der Datenbank.
 */
@Dao
interface OrganizerDao {
    /**
     * Fügt einen oder mehrere Veranstalterdatensätze in die Datenbank ein.
     */
    @Insert
    suspend fun insertAll(vararg organizers: Organizer)
}
