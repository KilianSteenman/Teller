package com.kiliansteenman.teller.logger.data

import kotlinx.coroutines.flow.Flow

interface TellerLogRepository {

    suspend fun addLog(log: TellerLog): TellerLog

    suspend fun get(id: Long): TellerLog

    fun search(query: String?): Flow<List<TellerLog>>

    fun clearLog()

    fun getFrameWorks(): List<String>

}
