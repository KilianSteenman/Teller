package com.kiliansteenman.teller.logger

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun Long.formatTimeStamp(): String {
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return formatter.format(Date(this))
}
