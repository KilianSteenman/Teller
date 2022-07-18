package com.kiliansteenman.teller.logger.ui

import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.data.TellerLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

internal class FakeTellerLogRepository(
    initialList: List<TellerLog> = emptyList()
) : TellerLogRepository {

    private val logs: MutableStateFlow<List<TellerLog>> = MutableStateFlow(initialList)

    var clearCount = 0

    override suspend fun addLog(log: TellerLog): TellerLog {
        logs.tryEmit(logs.value.toMutableList().apply { add(log) })
        return log
    }

    override suspend fun get(id: Long): TellerLog {
        return logs.value.find { it.id == id }!!
    }

    override fun search(query: String): Flow<List<TellerLog>> =
        logs.map { it.filter { log -> log.params.toString().contains(query) } }

    override fun clearLog() {
        logs.tryEmit(emptyList())
        clearCount++
    }

    override fun getFrameWorks(): List<String> {
        TODO("Not yet implemented")
    }
}