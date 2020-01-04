package com.kiliansteenman.beancounter.logger.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.kiliansteenman.beancounter.logger.data.AnalyticsLogEvent
import com.kiliansteenman.beancounter.logger.data.AnalyticsLogRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class RoomAnalyticsLogRepository(
    context: Context
) : AnalyticsLogRepository {

    private val analyticsLogEventDao: AnalyticsLogEventDao =
        Room.databaseBuilder(
            context.applicationContext,
            BeanCounterDatabase::class.java, "database-name"
        ).fallbackToDestructiveMigration()
            .build()
            .analyticsLogEventDao()

    private val executor: Executor = Executors.newSingleThreadExecutor()

    override fun addLog(log: AnalyticsLogEvent) {
        executor.execute { analyticsLogEventDao.insertAll(log) }
    }

    override fun getAll(): LiveData<List<AnalyticsLogEvent>> = analyticsLogEventDao.getAll()

    override fun clearLog() {
        executor.execute { analyticsLogEventDao.clearAll() }
    }

    override fun getFrameWorks(): List<String> = analyticsLogEventDao.getFrameWorks()
}