package com.example.eventradar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eventradar.R
import com.example.eventradar.activities.MainActivity

/**
 * Fragment zur Darstellung einer interaktiven Karte für Veranstaltungsorte.
 */
class MapFragment : Fragment() {
    /**
     * Erstellt die Ansicht für das Kartenfragment und initialisiert die Suchleiste.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val root = inflater.inflate(R.layout.fragment_map, container, false)

        MainActivity.setupSearchBar(root)

        return root
    }
}
