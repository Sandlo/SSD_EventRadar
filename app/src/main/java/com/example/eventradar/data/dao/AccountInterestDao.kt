package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.eventradar.data.entities.AccountInterest

/**
 * Data Access Object (DAO) Schnittstelle für den Zugriff auf Account-Interessen-Daten in der Datenbank.
 */
@Dao
interface AccountInterestDao {
    /**
     * Fügt ein oder mehrere Interessen in die Datenbank ein.
     */
    @Insert
    suspend fun insertAll(vararg interests: AccountInterest)

    /**
     * Alle Interessen eines Benutzers aus der Datenbank.
     */
    @Query("SELECT * FROM account_interest WHERE account_id = :userId")
    suspend fun getUserInterests(userId: Long): List<AccountInterest>
}
