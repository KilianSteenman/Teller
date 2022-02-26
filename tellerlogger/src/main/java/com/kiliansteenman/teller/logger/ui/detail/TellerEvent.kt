package com.kiliansteenman.teller.logger.ui.detail

internal sealed class TellerEvent {
    data class ShareLog(val sharableLogs: String) : TellerEvent()
}
