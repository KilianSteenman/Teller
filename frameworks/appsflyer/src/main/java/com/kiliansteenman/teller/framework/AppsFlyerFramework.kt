package com.kiliansteenman.teller.framework

import android.content.Context
import com.appsflyer.AppsFlyerLib
import com.kiliansteenman.teller.Framework
import com.kiliansteenman.teller.Measurement

class AppsFlyerFramework(
    context: Context
) : Framework {

    private val context: Context = context.applicationContext

    override val name: String = NAME

    override fun count(measurement: Measurement) {
        AppsFlyerLib.getInstance().logEvent(context, measurement.name, measurement.params)
    }

    companion object {
        const val NAME = "AppsFlyer"
    }
}