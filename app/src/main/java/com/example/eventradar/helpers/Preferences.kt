package com.example.eventradar.helpers

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * Hilfsklasse zur Verwaltung von Benutzereinstellungen.
 */
object Preferences {
    private const val ACCOUNT_ID = "accountId"

    /**
     * Konstante, die den Standardwert für ein nicht vorhandenes Benutzerkonto repräsentiert.
     */
    const val NO_ACCOUNT: Long = -1L

    /**
     * Überprüft, ob ein Benutzer angemeldet ist.
     *
     * @param context Der Kontext der Anwendung.
     * @return True, wenn ein Benutzer angemeldet ist, andernfalls False.
     */
    fun isLoggedIn(context: Context): Boolean =
        PreferenceManager
            .getDefaultSharedPreferences(context)
            .getLong(ACCOUNT_ID, NO_ACCOUNT) != NO_ACCOUNT

    /**
     * Legt fest, dass ein Benutzer angemeldet ist.
     *
     * @param context Der Kontext der Anwendung.
     * @param accountId Die ID des angemeldeten Benutzers.
     */
    fun setLoggedIn(
        context: Context,
        accountId: Long,
    ) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putLong(ACCOUNT_ID, accountId)
            .apply()
    }

    /**
     * Ruft die ID des angemeldeten Benutzers ab.
     *
     * @param context Der Kontext der Anwendung.
     * @return Die ID des angemeldeten Benutzers oder [NO_ACCOUNT], wenn kein Benutzer angemeldet ist.
     */
    fun getUserId(context: Context): Long =
        PreferenceManager
            .getDefaultSharedPreferences(context)
            .getLong(ACCOUNT_ID, NO_ACCOUNT)
}
