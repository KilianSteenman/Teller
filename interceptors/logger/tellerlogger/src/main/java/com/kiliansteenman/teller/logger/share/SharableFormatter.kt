package com.kiliansteenman.teller.logger.share

import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.formatTimeStamp
import com.kiliansteenman.teller.logger.prettyFormat

internal fun TellerLog.toSharableContent(): String =
    StringBuilder()
        .append("$framework - $title \n\n")
        .append("Type: $type \n")
        .append("Time: ${logDate.formatTimeStamp()} \n")
        .append("Params: \n${params.prettyFormat("-")}")
        .toString()
