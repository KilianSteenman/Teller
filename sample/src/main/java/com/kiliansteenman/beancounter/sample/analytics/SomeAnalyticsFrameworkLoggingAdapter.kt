package com.kiliansteenman.beancounter.sample.analytics

import com.kiliansteenman.beancounter.logger.data.AnalyticsLogEvent
import com.kiliansteenman.beancounter.logger.LoggingAdapter

class SomeAnalyticsFrameworkLoggingAdapter :
    LoggingAdapter<SomeAnalyticsFrameworkEvent> {

    override fun toLogEvent(event: SomeAnalyticsFrameworkEvent): AnalyticsLogEvent =
        AnalyticsLogEvent(
            framework = "SomeFramework",
            type = "ScreenView",
            title = event.name,
            content = event.params.map { entry -> "${entry.key}: ${entry.value}" }
                .joinToString(separator = "\n")
        )
}