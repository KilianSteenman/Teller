package com.kiliansteenman.teller.logger

import android.content.Context
import android.content.Intent
import androidx.core.app.ShareCompat

internal fun Context.getShareIntent(
    intentTitle: String,
    intentSubject: String,
    content: String,
): Intent = ShareCompat.IntentBuilder(this)
    .setType("text/plain")
    .setChooserTitle(intentTitle)
    .setSubject(intentSubject)
    .setText(content)
    .createChooserIntent()
