package com.kiliansteenman.teller

class MeasurementChain private constructor(
    private val framework: Framework,
    private val interceptors: List<MeasurementInterceptor>
) {

    val frameworkName: String
        get() = framework.name

    internal fun measure(measurement: Measurement) {
        var m = measurement
        interceptors.forEach { interceptor ->
            m = interceptor.process(m)
        }
        framework.count(m)
    }

    class Builder {

        private val interceptors = mutableListOf<MeasurementInterceptor>()

        fun addInterceptor(interceptor: MeasurementInterceptor) = this.apply {
            interceptors.add(interceptor)
        }

        fun build(framework: Framework): MeasurementChain {
            return MeasurementChain(framework, interceptors)
        }
    }
}