package com.kiliansteenman.teller

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class TellerTest {

    @AfterEach
    fun cleanup() {
        Teller.instance.clearAdapters()
    }

    @Test
    fun `when chain is registered for Framework, then chain is used to send measurement`() {
        val framework = FakeAnalyticsFramework()
        Teller.instance.apply {
            addMeasurementChain(MeasurementChain.Builder().build(framework))
        }

        Teller.instance.count(Measurement.Builder(framework.name, "ScreenView").build())

        assertTrue(framework.isInvoked)
    }

    @Test
    fun `when multiple Frameworks are registered, the correct Framework is used`() {
        val framework1 = FakeAnalyticsFramework("framework1")
        val framework2 = FakeAnalyticsFramework("framework2")
        Teller.instance.apply {
            addMeasurementChain(MeasurementChain.Builder().build(framework1))
            addMeasurementChain(MeasurementChain.Builder().build(framework2))
        }

        Teller.instance.count(Measurement.Builder(framework1.name, "ScreenView").build())

        assertTrue(framework1.isInvoked)
        assertFalse(framework2.isInvoked)
    }
}

internal open class FakeAnalyticsFramework(
    override val name: String = "Fake"
) : Framework {

    var isInvoked = false

    override fun count(measurement: Measurement) {
        isInvoked = true
    }
}
