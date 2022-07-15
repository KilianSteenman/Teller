package com.kiliansteenman.teller

class Teller private constructor() {

    private val mapping = mutableMapOf<String, MeasurementChain>()

    fun addMeasurementChain(chain: MeasurementChain) {
        mapping[chain.framework.name] = chain
    }

    fun clearAdapters() {
        mapping.clear()
    }

    fun count(measurement: Measurement) {
        mapping[measurement.framework]?.measure(measurement)
    }

    companion object {

        val instance: Teller by lazy { Teller() }
    }
}
