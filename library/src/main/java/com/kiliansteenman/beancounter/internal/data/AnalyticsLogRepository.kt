package com.kiliansteenman.beancounter.internal.data

import android.content.SharedPreferences

interface AnalyticsLogRepository {

    fun addLog(log: AnalyticsLogEvent)

    fun getAll(): List<AnalyticsLogEvent>

    fun clearLog()

}