package com.kiliansteenman.teller.sample.adobe

import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.LoggingAdapter

internal class AdobeAnalyticsLoggingAdapter : LoggingAdapter<AdobeEvent> {

    override fun toLogEvent(event: AdobeEvent): TellerLog =
        TellerLog(
            framework = "Adobe Analytics",
            type = "ScreenView",
            title = event.name,
            content = event.data.map { entry -> "${entry.key}: ${entry.value}" }
                .joinToString(separator = "\n")
        )
}
