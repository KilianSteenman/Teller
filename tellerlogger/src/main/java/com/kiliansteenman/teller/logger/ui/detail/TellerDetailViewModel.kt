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
import kotlinx.coroutines.Dispatchers
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

    private val _logEvent = MutableLiveData<TellerLog>()
    val logEvent: LiveData<TellerLog> = _logEvent

    init {
        viewModelScope.launch(Dispatchers.Default) {
            val log = db.tellerLogDao().get(logId)
            _logEvent.postValue(log)
        }
    }

    companion object {
        internal const val PARAM_LOG_ID = "PARAM_LOG_ID"
    }
}
