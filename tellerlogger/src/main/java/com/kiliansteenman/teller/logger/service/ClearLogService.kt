package com.kiliansteenman.teller.logger.service

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.kiliansteenman.teller.logger.data.RepositoryProvider
import com.kiliansteenman.teller.logger.ui.TellerLogNotification
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

internal class ClearLogService : JobIntentService() {
    private val scope = MainScope()

    override fun onHandleWork(intent: Intent) {
        scope.launch {
            RepositoryProvider.getRepository(applicationContext).clearLog()
            TellerLogNotification.clearBuffer()
            TellerLogNotification(applicationContext).dismissNotifications()
        }
    }

    companion object {
        private const val CLEAN_LOGS_JOB_ID = 123320

        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, ClearLogService::class.java, CLEAN_LOGS_JOB_ID, work)
        }
    }
}
