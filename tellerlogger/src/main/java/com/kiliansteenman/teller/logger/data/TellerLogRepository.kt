package com.kiliansteenman.teller.logger.data

import kotlinx.coroutines.flow.Flow

interface TellerLogRepository {

    suspend fun addLog(log: TellerLog): TellerLog

    fun getAll(): Flow<List<TellerLog>>

    fun clearLog()

    fun getFrameWorks(): List<String>

}
