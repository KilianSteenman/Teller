package com.kiliansteenman.teller.logger.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.data.TellerLogRepository
import com.kiliansteenman.teller.logger.data.RepositoryProvider

internal class TellerLogViewModel(application: Application) : AndroidViewModel(application) {

    private val logRepository: TellerLogRepository =
        RepositoryProvider.getRepository(application)

    val logEvents: LiveData<List<TellerLog>> = logRepository.getAll()

    fun onClearLogClicked(): Boolean {
        logRepository.clearLog()
        return true
    }
}