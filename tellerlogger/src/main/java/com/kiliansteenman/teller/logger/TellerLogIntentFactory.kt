package com.kiliansteenman.teller.logger

import android.content.Context
import android.content.Intent
import com.kiliansteenman.teller.logger.ui.overview.TellerLogActivity

object TellerLogIntentFactory {

    fun createIntent(context: Context): Intent =
        Intent(context, TellerLogActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
}