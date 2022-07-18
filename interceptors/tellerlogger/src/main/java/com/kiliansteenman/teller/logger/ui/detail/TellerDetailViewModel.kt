package com.kiliansteenman.teller.logger.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiliansteenman.teller.logger.data.TellerLogRepository
import com.kiliansteenman.teller.logger.share.toSharableContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class TellerDetailViewModel(
    private val repository: TellerLogRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val logId = savedStateHandle.get<Long>(PARAM_LOG_ID)
        ?: throw IllegalArgumentException("Log id argument should not be null")

    private val _events = MutableSharedFlow<TellerEvent>(0, extraBufferCapacity = Integer.MAX_VALUE)
    val events: Flow<TellerEvent> = _events

    private val _state: MutableStateFlow<TellerDetailViewState> =
        MutableStateFlow(TellerDetailViewState.Loading)
    val state: StateFlow<TellerDetailViewState> = _state

    init {
        viewModelScope.launch(Dispatchers.Default) {
            val log = repository.get(logId)
            _state.value = TellerDetailViewState.Success(log)
        }
    }

    fun onShareLogClicked() {
        viewModelScope.launch(Dispatchers.Default) {
            (_state.value as? TellerDetailViewState.Success)?.let { successState ->
                _events.tryEmit(TellerEvent.ShareLog(successState.tellerLog.toSharableContent()))
            }
        }
    }

    companion object {
        internal const val PARAM_LOG_ID = "PARAM_LOG_ID"
    }
}
