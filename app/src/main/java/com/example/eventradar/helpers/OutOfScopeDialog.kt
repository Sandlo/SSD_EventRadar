package com.example.eventradar.helpers

import android.content.Context
import com.example.eventradar.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Hilfsklasse zur Anzeige eines Dialogs, der anzeigt, dass der Benutzer den Anwendungsbereich verlassen hat.
 */
object OutOfScopeDialog {
    /**
     * Zeigt den Out-of-Scope-Dialog an.
     *
     * @param context Der Kontext der Anwendung.
     */
    fun show(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.scope)
            .setMessage(R.string.scope_summary)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .show()
    }
}
