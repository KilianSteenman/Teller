package com.kiliansteenman.beancounter.internal.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kiliansteenman.beancounter.internal.data.AnalyticsLogEvent
import com.kiliansteenman.beancounter.internal.data.AnalyticsLogRepository
import com.kiliansteenman.beancounter.internal.data.room.RoomAnalyticsLogRepository

class BeanCounterViewModel(application: Application) : AndroidViewModel(application) {

    private val logRepository: AnalyticsLogRepository = RoomAnalyticsLogRepository(application)

    val logEvents: LiveData<List<AnalyticsLogEvent>> = logRepository.getAll()

    fun onClearLogClicked(): Boolean {
        logRepository.clearLog()
        return true
    }
}