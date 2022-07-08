package com.kiliansteenman.teller.logger.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiliansteenman.teller.logger.R
import com.kiliansteenman.teller.logger.data.TellerLogRepository
import com.kiliansteenman.teller.logger.ui.notification.TellerLogNotification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class TellerLogViewModel(
    private val logRepository: TellerLogRepository
) : ViewModel() {

    private val queryFlow: MutableStateFlow<String> = MutableStateFlow(EMPTY_QUERY)

    val state: StateFlow<OverViewState> = queryFlow
        .flatMapLatest { logRepository.search(it) }
        .map { logs ->
            if (logs.isEmpty()) {
                OverViewState.Error(R.string.teller_error_empty)
            } else {
                OverViewState.Content(logs)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = OverViewState.Loading
        )

    fun onClearLogClicked(): Boolean {
        logRepository.clearLog()
        TellerLogNotification.clearBuffer()
        return true
    }

    fun onQueryChanged(query: String?) {
        queryFlow.tryEmit(query ?: EMPTY_QUERY)
    }

    companion object {
        private const val EMPTY_QUERY = ""
    }
}
