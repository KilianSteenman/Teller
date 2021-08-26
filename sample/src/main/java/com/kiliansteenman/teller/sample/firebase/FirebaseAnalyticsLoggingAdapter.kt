package com.kiliansteenman.teller.sample.firebase

import com.kiliansteenman.teller.logger.LoggingAdapter
import com.kiliansteenman.teller.logger.data.TellerLog

internal class FirebaseAnalyticsLoggingAdapter : LoggingAdapter<FirebaseEvent> {

    override fun toLogEvent(event: FirebaseEvent): TellerLog =
        TellerLog(
            framework = "Firebase",
            type = "Event",
            title = event.name,
            content = event.params.map { entry -> "${entry.key}: ${entry.value}" }
                .joinToString(separator = "\n")
        )
}
