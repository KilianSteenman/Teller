package com.kiliansteenman.teller.logger

import android.content.Context

object LoggerProvider {

    fun createAnalyticsLogger(context: Context) =
        DefaultTellerLogger()
}