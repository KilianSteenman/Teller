package com.kiliansteenman.beancounter.logger

import com.kiliansteenman.beancounter.logger.data.AnalyticsLogEvent

interface LoggingAdapter<T> {

    fun toLogEvent(event: T): AnalyticsLogEvent
}