package com.kiliansteenman.teller.logger.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kiliansteenman.teller.logger.data.RepositoryProvider
import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.data.TellerLogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

internal class TellerLogViewModel(application: Application) : AndroidViewModel(application) {

    private val logRepository: TellerLogRepository =
        RepositoryProvider.getRepository(application)

    private val queryFlow: MutableStateFlow<String> = MutableStateFlow("")

    val state: StateFlow<List<TellerLog>> = queryFlow
        .flatMapLatest { logRepository.search(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = emptyList())

    fun onClearLogClicked(): Boolean {
        logRepository.clearLog()
        TellerLogNotification.clearBuffer()
        return true
    }

    fun onQueryChanged(query: String?) {
        queryFlow.tryEmit(query ?: "")
    }
}
