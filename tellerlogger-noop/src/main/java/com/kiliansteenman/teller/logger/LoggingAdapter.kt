package com.kiliansteenman.teller.logger

import com.kiliansteenman.teller.logger.data.TellerLog

interface LoggingAdapter<T> {

    fun toLogEvent(event: T): TellerLog
}