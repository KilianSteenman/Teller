package com.kiliansteenman.teller.framework

import com.kiliansteenman.teller.Measurement

class AppsFlyerBuilder(
    name: String
) : Measurement.Builder(
    framework = AppsFlyerFramework.NAME,
    type = "Event",
    name = name
)