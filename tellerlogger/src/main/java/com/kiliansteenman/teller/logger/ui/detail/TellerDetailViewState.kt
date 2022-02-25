package com.kiliansteenman.teller.logger.ui.detail

import com.kiliansteenman.teller.logger.data.TellerLog

sealed class TellerDetailViewState {

    object Loading : TellerDetailViewState()

    data class Success(val tellerLog: TellerLog) : TellerDetailViewState()

}
