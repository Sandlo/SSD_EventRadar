package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Die Klasse Ticket repräsentiert ein Ticket in der Room-Datenbank.
 *
 * @property eventId Die ID des zugehörigen Events.
 * @property userId Die ID des Benutzers, der das Ticket gekauft hat.
 * @property purchasedAt Der Zeitpunkt, zu dem das Ticket gekauft wurde.
 */
@Entity(tableName = "ticket")
class Ticket(
    @ColumnInfo(name = "event_id") val eventId: Long,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "purchased_at") val purchasedAt: Long,
) {
    /**
     * Die eindeutige ID des Tickets in der Datenbank.
     */
    @ColumnInfo(name = "ticket_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
