package com.kiliansteenman.teller.sample.adobe

import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.LoggingAdapter

internal class AdobeStateLoggingAdapter : LoggingAdapter<AdobeState> {

    override fun toLogEvent(event: AdobeState): TellerLog =
        TellerLog(
            framework = "Adobe Analytics",
            type = "State",
            title = event.name,
            content = event.data.map { entry -> "${entry.key}: ${entry.value}" }
                .joinToString(separator = "\n")
        )
}

internal class AdobeActionLoggingAdapter : LoggingAdapter<AdobeAction> {

    override fun toLogEvent(event: AdobeAction): TellerLog =
        TellerLog(
            framework = "Adobe Analytics",
            type = "Action",
            title = event.name,
            content = event.data.map { entry -> "${entry.key}: ${entry.value}" }
                .joinToString(separator = "\n")
        )
}


