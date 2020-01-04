package com.kiliansteenman.beancounter.logger.data

import androidx.lifecycle.LiveData

interface AnalyticsLogRepository {

    fun addLog(log: AnalyticsLogEvent)

    fun getAll(): LiveData<List<AnalyticsLogEvent>>

    fun clearLog()

    fun getFrameWorks(): List<String>

}