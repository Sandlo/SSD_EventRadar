package com.example.eventradar.data.entities

import android.content.res.Resources
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.eventradar.R
import com.example.eventradar.data.SimpleListItem

/**
 * Die Klasse TicketWithEvent stellt eine Beziehung zwischen einem Ticket und einem Event in der Room-Datenbank dar.
 *
 * @property ticket Das Ticket, das mit dem Event verknüpft ist.
 * @property event Das Event, mit dem das Ticket verknüpft ist.
 */
@Entity
data class TicketWithEvent(
    @Embedded val ticket: Ticket,
    @Relation(
        parentColumn = "event_id",
        entityColumn = "event_id",
    )
    val event: Event,
) {
    /**
     * Diese Methode konvertiert das TicketWithEvent-Objekt in ein SimpleListItem-Objekt für die
     * Anzeige in einer RecyclerView.
     *
     * @param resources Die Ressourcen.
     * @return Ein SimpleListItem-Objekt, das die Informationen des Events enthält.
     */
    fun toListItem(resources: Resources): SimpleListItem =
        SimpleListItem(
            event.title,
            event.getSummary(resources),
            R.drawable.ic_circle_tag,
        )
}
