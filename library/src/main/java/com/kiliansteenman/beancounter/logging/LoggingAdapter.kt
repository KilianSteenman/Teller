package com.kiliansteenman.beancounter.logging

import com.kiliansteenman.beancounter.internal.data.AnalyticsLogEvent

interface LoggingAdapter<T> {

    fun toLogEvent(event: T): AnalyticsLogEvent

    fun getTitle(event: T): String

    fun getContent(event: T): String
}