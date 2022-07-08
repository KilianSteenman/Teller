package com.kiliansteenman.teller.logger.data.room

import android.content.Context
import androidx.room.Room
import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.data.TellerLogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor
import java.util.concurrent.Executors

internal class RoomTellerLogRepository(
    context: Context
) : TellerLogRepository {

    private val tellerLogDao: TellerLogDao =
        Room.databaseBuilder(
            context.applicationContext,
            TellerLogDatabase::class.java, "database-name"
        ).fallbackToDestructiveMigration()
            .build()
            .tellerLogDao()

    private val executor: Executor = Executors.newSingleThreadExecutor()

    override suspend fun addLog(log: TellerLog): TellerLog = withContext(Dispatchers.IO) {
        val generatedId = tellerLogDao.insert(log)
        tellerLogDao.get(generatedId)
    }

    override suspend fun get(id: Long): TellerLog = tellerLogDao.get(id)

    override fun search(query: String?): Flow<List<TellerLog>> = tellerLogDao.search(query)

    override fun clearLog() {
        executor.execute { tellerLogDao.clearAll() }
    }

    override fun getFrameWorks(): List<String> = tellerLogDao.getFrameWorks()
}
