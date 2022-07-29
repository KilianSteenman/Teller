package com.kiliansteenman.teller.logger

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun Long.formatTimeStamp(): String {
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return formatter.format(Date(this))
}

internal fun Map<String, String>.prettyFormat(emptyPlaceHolder: String = ""): String {
    if (this.isEmpty()) return emptyPlaceHolder

    return this.entries.joinToString("\n") { entry -> "${entry.key}: ${entry.value}" }
}