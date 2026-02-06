package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.eventradar.data.entities.User

/**
 * Data Access Object (DAO) Schnittstelle für den Zugriff auf Account-Daten in der Datenbank.
 */
@Dao
interface UserDao {
    /**
     * Fügt einen oder mehrere Accounts in die Datenbank ein.
     */
    @Insert
    suspend fun insertAll(vararg accounts: User)

    @androidx.room.Query("SELECT * FROM user")
    suspend fun getAll(): List<User>
}
