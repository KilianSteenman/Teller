package com.kiliansteenman.teller.logger

import android.content.Context

object LoggerProvider {

    @Suppress("Unused", "UNUSED_PARAMETER")
    fun createAnalyticsLogger(context: Context) =
        DefaultTellerLogger()
}