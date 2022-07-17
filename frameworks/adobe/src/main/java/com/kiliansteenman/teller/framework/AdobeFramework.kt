package com.kiliansteenman.teller.framework

import com.adobe.marketing.mobile.MobileCore
import com.kiliansteenman.teller.Framework
import com.kiliansteenman.teller.Measurement

class AdobeFramework : Framework {

    override val name: String = NAME

    override fun count(measurement: Measurement) {
        MobileCore.trackAction(measurement.name, measurement.params.mapValues { it.toString() })
    }

    companion object {
        const val NAME = "Adobe"
    }
}