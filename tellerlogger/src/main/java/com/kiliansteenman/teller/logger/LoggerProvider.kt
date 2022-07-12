package com.kiliansteenman.teller.logger

import android.content.Context
import com.kiliansteenman.teller.logger.data.RepositoryProvider
import com.kiliansteenman.teller.logger.ui.notification.TellerLogNotification

object LoggerProvider {

    fun createAnalyticsLogger(context: Context): DefaultTellerLogger =
        DefaultTellerLogger(
            RepositoryProvider.getRepository(context),
            TellerLogNotification(context)
        )
}