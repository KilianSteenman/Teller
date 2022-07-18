package com.kiliansteenman.teller.logger.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kiliansteenman.teller.logger.data.TellerLogRepository

internal class TellerLogViewModelFactory(
    private val repository: TellerLogRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TellerLogViewModel::class.java)) {
            return TellerLogViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}