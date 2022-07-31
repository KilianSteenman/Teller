package com.kiliansteenman.teller.framework

import com.kiliansteenman.teller.Measurement

class FirebaseBuilder(
    name: String
) : Measurement.Builder(
    framework = FirebaseFramework.NAME,
    type = "Event",
    name = name
)