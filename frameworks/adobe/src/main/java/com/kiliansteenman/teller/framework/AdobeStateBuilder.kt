package com.kiliansteenman.teller.framework

import com.kiliansteenman.teller.Measurement

class AdobeStateBuilder(
    state: String
) : Measurement.Builder(
    framework = AdobeFramework.NAME,
    type = "State",
    name = state
)