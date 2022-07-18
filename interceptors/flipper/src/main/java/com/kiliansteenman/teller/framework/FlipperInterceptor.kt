package com.kiliansteenman.teller.framework

import com.facebook.flipper.core.FlipperConnection
import com.facebook.flipper.core.FlipperObject
import com.facebook.flipper.core.FlipperPlugin
import com.kiliansteenman.teller.Measurement
import com.kiliansteenman.teller.MeasurementInterceptor

class FlipperInterceptor : MeasurementInterceptor, FlipperPlugin {

    private var connection: FlipperConnection? = null

    override fun getId(): String = PLUGIN_NAME

    override fun onConnect(connection: FlipperConnection?) {
        this.connection = connection
    }

    override fun onDisconnect() {
        connection = null
    }

    override fun runInBackground(): Boolean = false

    override fun process(measurement: Measurement): Measurement {
        connection?.send("newRow", measurement.toFlipperObject())
        return measurement
    }

    private fun Measurement.toFlipperObject() =
        FlipperObject.Builder()
            .put(PARAM_FRAMEWORK, this.framework)
            .put(PARAM_TYPE, this.type)
            .put(PARAM_NAME, this.name)
            .put(PARAM_PARAMS, this.params)
            .build()

    private companion object {
        private const val PLUGIN_NAME = "teller"

        private const val PARAM_FRAMEWORK = "framework"
        private const val PARAM_TYPE = "type"
        private const val PARAM_NAME = "name"
        private const val PARAM_PARAMS = "params"
    }
}