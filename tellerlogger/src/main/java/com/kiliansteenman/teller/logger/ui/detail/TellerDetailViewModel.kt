package com.kiliansteenman.teller.logger.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.data.room.TellerLogDatabase
import com.kiliansteenman.teller.logger.share.toSharableContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

internal class TellerDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

    private val db: TellerLogDatabase
        get() {
            return Room.databaseBuilder(
                getApplication(),
                TellerLogDatabase::class.java, "database-name"
            ).build()
        }

    private val logId = savedStateHandle.get<Long>(PARAM_LOG_ID)
        ?: throw IllegalArgumentException("Log id argument should not be null")

    private val _events = MutableSharedFlow<TellerEvent>(0, extraBufferCapacity = Integer.MAX_VALUE)
    val events: Flow<TellerEvent> = _events

    private val _logEvent = MutableLiveData<TellerLog>()
    val logEvent: LiveData<TellerLog> = _logEvent

    init {
        viewModelScope.launch(Dispatchers.Default) {
            val log = db.tellerLogDao().get(logId)
            _logEvent.postValue(log)
        }
    }

    fun onShareLogClicked() {
        viewModelScope.launch(Dispatchers.Default) {
            _logEvent.value?.let { log ->
                _events.tryEmit(TellerEvent.ShareLog(log.toSharableContent()))
            }
        }
    }

    companion object {
        internal const val PARAM_LOG_ID = "PARAM_LOG_ID"
    }
}
