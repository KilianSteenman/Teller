package com.kiliansteenman.beancounter.sample.analytics

import com.kiliansteenman.beancounter.internal.data.AnalyticsLogEvent
import com.kiliansteenman.beancounter.logging.LoggingAdapter

class SomeAnalyticsFrameworkLoggingAdapter : LoggingAdapter<SomeAnalyticsFrameworkEvent> {

    override fun getTitle(event: SomeAnalyticsFrameworkEvent): String =
        "Some analytics framework event"

    override fun getContent(event: SomeAnalyticsFrameworkEvent): String = event.name

    override fun toLogEvent(event: SomeAnalyticsFrameworkEvent): AnalyticsLogEvent =
        AnalyticsLogEvent(
            framework = "SomeFramework",
            type = "ScreenView",
            logDate = System.currentTimeMillis(),
            title = event.name,
            content = event.params.map { entry -> "${entry.key}: ${entry.value}" }
                .joinToString(separator = "\n")
        )
}