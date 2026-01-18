package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.ErrorAdapter
import com.example.eventradar.adapters.LoadingAdapter
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.data.entities.TicketWithEventWithAddress
import com.example.eventradar.helpers.External
import com.example.eventradar.helpers.OutOfScopeDialog
import com.example.eventradar.helpers.Preferences
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Aktivität zur Anzeige von Ticketinformationen und Interaktionsmöglichkeiten wie Stornierung.
 */
class TicketActivity : BaseActivity(), RecyclerViewHelperInterface {
    private var ticket: TicketWithEventWithAddress? = null

    /**
     * Initialisiert die Ticketaktivität und lädt Ticketdetails in eine Liste.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    startActivity(
                        Intent(this@TicketActivity, MainActivity::class.java).apply {
                            action = "com.example.eventradar.SHOW_TICKETS"
                        },
                    )
                    finish()
                }
            },
        )

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = LoadingAdapter()

        if (!intent.hasExtra(TICKET_INTENT_EXTRA)) {
            recyclerView.adapter = ErrorAdapter()
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            ticket =
                AppDatabase.getInstance(this@TicketActivity).ticketDao()
                    .getWithEventWithAddress(
                        intent.getLongExtra(TICKET_INTENT_EXTRA, -1),
                        Preferences.getUserId(this@TicketActivity),
                    )
            if (ticket != null) {
                recyclerView.adapter =
                    SimpleListAdapter(
                        listOf(
                            SimpleListItem("", resources.getString(R.string.ticket_info)),
                            SimpleListItem(
                                ticket?.event?.event?.title ?: error(TICKET_IS_NULL),
                                resources.getString(R.string.what),
                                R.drawable.ic_circle_local_activity,
                            ),
                            SimpleListItem(
                                ticket?.event?.event?.getStartAsString(resources) ?: error(TICKET_IS_NULL),
                                resources.getString(R.string.`when`),
                                R.drawable.ic_circle_calendar_today,
                            ),
                            SimpleListItem(
                                ticket?.event?.address?.toString(resources) ?: error(TICKET_IS_NULL),
                                resources.getString(R.string.where),
                                R.drawable.ic_circle_location_on,
                            ),
                            SimpleListItem(resources.getString(R.string.ticket_cancel)),
                        ),
                        this@TicketActivity,
                    )
            } else {
                recyclerView.adapter = ErrorAdapter()
            }
        }
    }

    /**
     * Reagiert auf Klickereignisse in der Ticketliste, insbesondere bei Auswahl der Stornierungsoption.
     */
    override fun onItemClicked(position: Int) {
        when (position) {
            CANCELLATION_ITEM -> OutOfScopeDialog.show(this)
            LOCATION_ITEM -> External.openMaps(this, ticket?.event?.address ?: error(TICKET_IS_NULL))
            DATE_ITEM -> External.openCalendar(this, ticket?.event?.event ?: error(TICKET_IS_NULL))
        }
    }

    companion object {
        /**
         * Konstante für den Schlüssel, der verwendet wird, um Ticket-Daten als Intent-Extra
         * zwischen Aktivitäten zu übertragen.
         */
        const val TICKET_INTENT_EXTRA: String = "ticket_intent_extra"

        private const val CANCELLATION_ITEM = 4
        private const val LOCATION_ITEM = 3
        private const val DATE_ITEM = 2
        private const val TICKET_IS_NULL = "Ticket is null."
    }
}
