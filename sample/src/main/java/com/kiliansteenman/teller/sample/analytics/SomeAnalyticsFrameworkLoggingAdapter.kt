package com.kiliansteenman.teller.sample.analytics

import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.LoggingAdapter

class SomeAnalyticsFrameworkLoggingAdapter :
    LoggingAdapter<SomeAnalyticsFrameworkEvent> {

    override fun toLogEvent(event: SomeAnalyticsFrameworkEvent): TellerLog =
        TellerLog(
            framework = "SomeFramework",
            type = "ScreenView",
            title = event.name,
            content = event.params.map { entry -> "${entry.key}: ${entry.value}" }
                .joinToString(separator = "\n")
        )
}