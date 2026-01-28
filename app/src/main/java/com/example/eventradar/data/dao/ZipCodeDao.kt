package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.eventradar.data.entities.ZipCode

/**
 * Data Access Object (DAO) Schnittstelle für den Zugriff auf Postleitzahldaten in der Datenbank.
 */
@Dao
interface ZipCodeDao {
    /**
     * Fügt einen oder mehrere Postleitzahldatensätze in die Datenbank ein.
     */
    @Insert
    suspend fun insertAll(vararg zipCodes: ZipCode)
}
