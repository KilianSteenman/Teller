package com.kiliansteenman.beancounter.logging

import android.content.Context
import android.content.Intent
import com.kiliansteenman.beancounter.internal.ui.BeanCounterActivity

object BeanCounterIntentFactory {

    fun createIntent(context: Context): Intent? {
        return Intent(context, BeanCounterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}