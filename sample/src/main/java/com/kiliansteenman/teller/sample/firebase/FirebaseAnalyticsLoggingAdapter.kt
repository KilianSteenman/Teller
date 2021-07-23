package com.kiliansteenman.teller.sample.firebase

import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.LoggingAdapter

internal class FirebaseAnalyticsLoggingAdapter : LoggingAdapter<FirebaseEvent> {

    override fun toLogEvent(event: FirebaseEvent): TellerLog =
        TellerLog(
            framework = "Google Analytics",
            type = "ScreenView",
            title = event.name,
            content = event.params.map { entry -> "${entry.key}: ${entry.value}" }
                .joinToString(separator = "\n")
        )
}
