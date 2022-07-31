package com.kiliansteenman.teller.framework

import com.kiliansteenman.teller.Measurement

class AdobeActionBuilder(
    action: String,
) : Measurement.Builder(
    framework = AdobeFramework.NAME,
    type = "Action",
    name = action
)