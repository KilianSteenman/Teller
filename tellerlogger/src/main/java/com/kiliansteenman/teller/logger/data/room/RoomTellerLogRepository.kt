package com.kiliansteenman.teller.logger.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.data.TellerLogRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class RoomTellerLogRepository(
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

    override fun addLog(log: TellerLog) {
        executor.execute { tellerLogDao.insertAll(log) }
    }

    override fun getAll(): LiveData<List<TellerLog>> = tellerLogDao.getAll()

    override fun clearLog() {
        executor.execute { tellerLogDao.clearAll() }
    }

    override fun getFrameWorks(): List<String> = tellerLogDao.getFrameWorks()
}