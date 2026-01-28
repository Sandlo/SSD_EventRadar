package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.ErrorAdapter
import com.example.eventradar.adapters.LoadingAdapter
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.data.entities.Event
import com.example.eventradar.data.entities.Ticket
import com.example.eventradar.helpers.OutOfScopeDialog
import com.example.eventradar.helpers.Preferences
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Aktivität für Buchungsprozesse, zeigt verschiedene Buchungsoptionen an.
 */
class BookingActivity : BaseActivity(), RecyclerViewHelperInterface {
    /**
     * Initialisiert die Buchungsaktivität und konfiguriert die Anzeige von Eventdaten.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = LoadingAdapter()

        if (!intent.hasExtra(EventActivity.EVENT_INTENT_EXTRA)) {
            recyclerView.adapter = ErrorAdapter()
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            val event =
                AppDatabase.getInstance(this@BookingActivity).eventDao()
                    .get(intent.getLongExtra(EventActivity.EVENT_INTENT_EXTRA, -1))

            if (event != null) {
                showEvent(event, recyclerView)
                findViewById<ExtendedFloatingActionButton>(R.id.floating_action_button).setOnClickListener {
                    CoroutineScope(Dispatchers.Main).launch {
                        val ticket =
                            AppDatabase.getInstance(this@BookingActivity).ticketDao().insert(
                                Ticket(
                                    event.id,
                                    Preferences.getUserId(this@BookingActivity),
                                    System.currentTimeMillis(),
                                ),
                            )
                        startActivity(
                            Intent(this@BookingActivity, TicketActivity::class.java).putExtra(
                                TicketActivity.TICKET_INTENT_EXTRA,
                                ticket,
                            ),
                        )
                    }
                }
            } else {
                recyclerView.adapter = ErrorAdapter()
            }
        }
    }

    private fun showEvent(
        event: Event,
        recyclerView: RecyclerView,
    ) {
        recyclerView.adapter =
            SimpleListAdapter(
                listOf(
                    SimpleListItem(
                        event.title,
                        resources.getString(R.string.booking_title),
                        R.drawable.ic_circle_tag,
                    ),
                    SimpleListItem(
                        event.getPriceAsString(resources),
                        resources.getString(R.string.booking_price),
                        R.drawable.ic_circle_euro,
                    ),
                    SimpleListItem(
                        resources.getString(R.string.booking_payment_google),
                        resources.getString(R.string.booking_payment),
                        R.drawable.ic_square_google_pay,
                    ),
                    SimpleListItem("", resources.getString(R.string.booking_info)),
                ),
                this,
            )
    }

    /**
     * Reagiert auf Klickereignisse in der Buchungsliste, insbesondere bei Auswahl von Zahlungsanbietern.
     */
    override fun onItemClicked(position: Int) {
        if (position == PAYMENT_PROVIDER_ITEM) OutOfScopeDialog.show(this)
    }

    companion object {
        private const val PAYMENT_PROVIDER_ITEM = 2
    }
}
