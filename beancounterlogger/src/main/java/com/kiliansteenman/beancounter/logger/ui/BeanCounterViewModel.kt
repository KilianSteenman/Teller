package com.kiliansteenman.beancounter.logger.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kiliansteenman.beancounter.logger.data.AnalyticsLogEvent
import com.kiliansteenman.beancounter.logger.data.AnalyticsLogRepository
import com.kiliansteenman.beancounter.logger.data.room.RoomAnalyticsLogRepository

internal class BeanCounterViewModel(application: Application) : AndroidViewModel(application) {

    private val logRepository: AnalyticsLogRepository = RoomAnalyticsLogRepository(application)

    val logEvents: LiveData<List<AnalyticsLogEvent>> = logRepository.getAll()

    fun onClearLogClicked(): Boolean {
        logRepository.clearLog()
        return true
    }
}