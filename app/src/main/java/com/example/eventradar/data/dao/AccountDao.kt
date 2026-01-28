package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.eventradar.data.entities.Account

/**
 * Data Access Object (DAO) Schnittstelle für den Zugriff auf Account-Daten in der Datenbank.
 */
@Dao
interface AccountDao {
    /**
     * Sucht nach einem Account anhand der E-Mail oder Telefonnummer und gibt diesen zurück, falls vorhanden.
     */
    @Query("SELECT * FROM account WHERE e_mail = :eMailOrPhone OR phone = :eMailOrPhone LIMIT 1")
    suspend fun get(eMailOrPhone: String): Account?

    /**
     * Fügt einen oder mehrere Accounts in die Datenbank ein.
     */
    @Insert
    suspend fun insertAll(vararg accounts: Account)

    /**
     * Fügt einen Account in die Datenbank ein und gibt die ID des neuen Accounts zurück.
     */
    @Insert
    suspend fun insert(account: Account): Long
}
