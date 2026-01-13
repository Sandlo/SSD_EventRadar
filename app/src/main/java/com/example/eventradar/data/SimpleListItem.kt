package com.example.eventradar.data

/**
 * Datenklasse, die ein einfaches Listen-Element mit einem Titel, einer Zusammenfassung und einem Symbol repräsentiert.
 *
 * @param title Der Titel des Listen-Elements.
 * @param summary Die Zusammenfassung oder Beschreibung des Listen-Elements.
 * @param icon Die Ressourcen-ID des mit dem Listen-Element verbundenen Symbols.
 */
data class SimpleListItem(
    val title: String = "",
    val summary: String = "",
    val icon: Int = android.R.color.transparent,
)
