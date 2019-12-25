package com.kiliansteenman.beancounter

import android.content.Context
import android.content.Intent
import com.kiliansteenman.beancounter.internal.ui.BeanCounterActivity

object BeanCounterIntentFactory {

    fun createIntent(context: Context): Intent? {
        return Intent(context, BeanCounterActivity::class.java)
    }
}