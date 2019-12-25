package com.kiliansteenman.beancounter.internal.data

interface AnalyticsLogRepository {

    fun addLog(log: AnalyticsLogEvent)

    fun getAll(): List<AnalyticsLogEvent>

    fun clearLog()

}