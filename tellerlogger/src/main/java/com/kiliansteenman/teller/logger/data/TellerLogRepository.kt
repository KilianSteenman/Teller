package com.kiliansteenman.teller.logger.data

import kotlinx.coroutines.flow.Flow

interface TellerLogRepository {

    fun addLog(log: TellerLog)

    fun getAll(): Flow<List<TellerLog>>

    fun clearLog()

    fun getFrameWorks(): List<String>

}