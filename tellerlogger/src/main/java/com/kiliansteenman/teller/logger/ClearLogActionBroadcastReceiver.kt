package com.kiliansteenman.teller.logger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kiliansteenman.teller.logger.service.ClearLogService

internal class ClearLogActionBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        ClearLogService.enqueueWork(context, intent)
    }
}
