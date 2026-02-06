package com.example.eventradar.helpers

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.example.eventradar.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.util.Date

/**
 * DataExporter 2.1 (Fix für InterestDao)
 * Exportiert User, Account, Tickets und Interessen.
 */
object DataExporter {

    fun exportUserData(context: Context) {
        val userId = Preferences.getUserId(context)
        if (userId == -1L) {
            Toast.makeText(context, "Fehler: Kein User eingeloggt", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(context, "Export mit Tickets & Interessen...", Toast.LENGTH_SHORT).show()

        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getInstance(context)

            // 1. Stammdaten laden
            val accounts = db.accountDao().getAll()
            val users = db.userDao().getAll()

            val myAccount = accounts.find { it.id == userId }
            val myUser = users.find { it.accountId == userId }

            // 2. Tickets laden (NUR meine)
            // Nutzt die neue Methode 'getRawTickets' im TicketDao
            val myTickets = db.ticketDao().getRawTickets(userId)

            // 3. Interessen laden
            // a) Verknüpfungen (Nutzt neue Methode 'getAll' im AccountInterestDao)
            val allAccountInterests = db.accountInterestDao().getAll()

            // b) Namen (HIER WAR DER FEHLER: Wir nutzen jetzt deine existierende Methode!)
            val allInterests = db.interestDao().getAllInterests()

            // c) Filtern: Welche Interest-IDs gehören mir?
            val myInterestIds = allAccountInterests
                .filter { it.accountId == userId }
                .map { it.interestId }

            // d) Namen auflösen
            val myInterestNames = allInterests
                .filter { it.id in myInterestIds }
                .map { it.name }

            if (myAccount != null && myUser != null) {

                // 4. JSON zusammenbauen
                val ticketJsonList = myTickets.joinToString(",\n") { ticket ->
                    """
                    {
                        "ticket_id": ${ticket.id},
                        "event_id": ${ticket.eventId},
                        "kaufdatum_timestamp": ${ticket.purchasedAt},
                        "kaufdatum_lesbar": "${Date(ticket.purchasedAt)}"
                    }
                    """.trimIndent().prependIndent("                        ") // Rückt es passend ein
                }

                // Interessen als Liste ["Party", "Kultur"]
                val interestsJsonList = myInterestNames.joinToString(",") { "\"$it\"" }

                val jsonExport = """
                    {
                        "meta_info": {
                            "app": "EventRadar",
                            "export_date": "${Date()}",
                            "type": "FULL_DATA_EXPORT"
                        },
                        "account_data": {
                            "account_id": ${myAccount.id},
                            "email": "${myAccount.eMail}", 
                            "phone": "${myAccount.phone}"
                        },
                        "personal_data": {
                            "vorname": "${myUser.name}",
                            "nachname": "${myUser.surname}",
                            "geburtsdatum_timestamp": ${myUser.birthdate}
                        },
                        "saved_interests": [
                            $interestsJsonList
                        ],
                        "purchased_tickets": [
                            $ticketJsonList
                        ]
                    }
                """.trimIndent()

                saveToDownloads(context, jsonExport)
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "User-Daten nicht gefunden.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private suspend fun saveToDownloads(context: Context, jsonString: String) {
        try {
            val fileName = "EventRadar_FullExport_${System.currentTimeMillis()}.json"
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, fileName)

            FileWriter(file).use { it.write(jsonString) }

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Export komplett!\nDatei: Downloads/$fileName", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Fehler: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}