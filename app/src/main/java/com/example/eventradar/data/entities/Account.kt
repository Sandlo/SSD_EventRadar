package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Die Klasse Account repräsentiert ein Benutzerkonto in der Room-Datenbank.
 *
 * @property eMail Die E-Mail-Adresse des Benutzers.
 * @property phone Die Telefonnummer des Benutzers.
 * @property passwordHash Der gehashte Wert des Benutzerpassworts.
 */
@Entity(tableName = "account")
class Account(
    @ColumnInfo(name = "e_mail") val eMail: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "password_hash") val passwordHash: String,
) {
    /**
     * Die eindeutige ID des Benutzerkontos.
     */
    @ColumnInfo(name = "account_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
