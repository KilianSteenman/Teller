package com.kiliansteenman.teller.logger.data

import androidx.lifecycle.LiveData

interface TellerLogRepository {

    fun addLog(log: TellerLog)

    fun getAll(): LiveData<List<TellerLog>>

    fun clearLog()

    fun getFrameWorks(): List<String>

}