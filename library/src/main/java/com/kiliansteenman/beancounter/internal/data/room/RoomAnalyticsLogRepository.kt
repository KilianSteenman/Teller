package com.kiliansteenman.beancounter.internal.data.room

import com.kiliansteenman.beancounter.internal.data.AnalyticsLogEvent
import com.kiliansteenman.beancounter.internal.data.AnalyticsLogRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class RoomAnalyticsLogRepository(
    private val analyticsLogEventDao: AnalyticsLogEventDao
) : AnalyticsLogRepository {

    private val executor: Executor = Executors.newSingleThreadExecutor()

    override fun addLog(log: AnalyticsLogEvent) {
        executor.execute { analyticsLogEventDao.insertAll(log) }
    }

    override fun getAll(): List<AnalyticsLogEvent> {
        return analyticsLogEventDao.getAll()
    }

    override fun clearLog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}