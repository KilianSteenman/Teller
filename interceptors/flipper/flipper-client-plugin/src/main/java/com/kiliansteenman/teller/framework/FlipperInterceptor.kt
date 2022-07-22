package com.kiliansteenman.teller.framework

import com.facebook.flipper.core.FlipperConnection
import com.facebook.flipper.core.FlipperObject
import com.facebook.flipper.core.FlipperPlugin
import com.kiliansteenman.teller.Measurement
import com.kiliansteenman.teller.MeasurementInterceptor
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FlipperInterceptor : MeasurementInterceptor, FlipperPlugin {

    private var id: Int = 0
    private var connection: FlipperConnection? = null

    override fun getId(): String = PLUGIN_NAME

    override fun onConnect(connection: FlipperConnection?) {
        this.connection = connection
    }

    override fun onDisconnect() {
        connection = null
    }

    override fun runInBackground(): Boolean = true

    override fun process(measurement: Measurement): Measurement {
        connection?.send("newMeasurement", measurement.toFlipperObject())
        return measurement
    }

    private fun Measurement.toFlipperObject() =
        FlipperObject.Builder()
            .put(PARAM_ID, id++)
            .put(PARAM_TIMESTAMP, TIMESTAMP_FORMAT.format(Date()))
            .put(PARAM_FRAMEWORK, this.framework)
            .put(PARAM_TYPE, this.type)
            .put(PARAM_NAME, this.name)
            .put(PARAM_PARAMS, this.params)
            .build()

    private companion object {
        private const val PLUGIN_NAME = "Teller"

        private const val PARAM_ID = "id"
        private const val PARAM_TIMESTAMP = "timestamp"
        private const val PARAM_FRAMEWORK = "framework"
        private const val PARAM_TYPE = "type"
        private const val PARAM_NAME = "name"
        private const val PARAM_PARAMS = "params"

        private val TIMESTAMP_FORMAT = SimpleDateFormat.getTimeInstance()
    }
}