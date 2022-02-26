package com.kiliansteenman.teller.logger.share

import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.formatTimeStamp

internal fun TellerLog.toSharableContent(): String = StringBuilder()
    .apply {
        append("$framework - $title \n\n")
        append("Type: $type \n")
        append("Time: ${logDate.formatTimeStamp()} \n")
        val content = content.ifEmpty { "<no content>" }
        append("Content: \n$content")
    }
    .toString()
