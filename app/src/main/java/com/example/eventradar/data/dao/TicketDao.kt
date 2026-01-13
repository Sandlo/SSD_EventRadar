package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.eventradar.data.entities.Ticket
import com.example.eventradar.data.entities.TicketWithEvent
import com.example.eventradar.data.entities.TicketWithEventWithAddress

/**
 * Data Access Object (DAO) Schnittstelle für den Zugriff auf Ticketdaten in der Datenbank.
 */
@Dao
interface TicketDao {
    /**
     * Holt alle Tickets eines Benutzers mit den zugehörigen Veranstaltungsdaten.
     */
    @Transaction
    @Query("SELECT * FROM ticket WHERE user_id = :userId")
    suspend fun getAll(userId: Long): List<TicketWithEvent>

    /**
     * Holt ein spezifisches Ticket eines Benutzers mit zugehöriger Veranstaltung und Adresse.
     */
    @Transaction
    @Query("SELECT * FROM ticket WHERE ticket_id = :id AND user_id = :userId LIMIT 1")
    suspend fun getWithEventWithAddress(
        id: Long,
        userId: Long,
    ): TicketWithEventWithAddress?

    /**
     * Fügt einen oder mehrere Ticketdatensätze in die Datenbank ein.
     */
    @Insert
    suspend fun insertAll(vararg tickets: Ticket)

    /**
     * Fügt einen einzelnen Ticketdatensatz in die Datenbank ein und gibt die generierte Ticket-ID zurück.
     */
    @Insert
    suspend fun insert(ticket: Ticket): Long
}
