package com.kiliansteenman.beancounter.internal.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.kiliansteenman.beancounter.internal.data.AnalyticsLogEvent
import com.kiliansteenman.beancounter.internal.data.AnalyticsLogRepository
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFrameWorks(): List<String> = analyticsLogEventDao.getFrameWorks()
}