package com.kiliansteenman.teller

import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TellerTest {

    @After
    fun cleanup() {
        Teller.instance.clearAdapters()
    }

    @Test
    fun `when adapter is registered for type, then adapter is used`() {
        val eventAdapter = FakeAnalyticsAdapter<Event>()
        Teller.instance.apply {
            addAdapter(eventAdapter)
        }

        val event = Event("This an event name")
        Teller.instance.count(event)

        assertTrue(eventAdapter.isInvoked)
    }

    @Test
    fun `when multiple adapters are registered for different types, the correct adapter is used`() {
        val eventAdapter = FakeAnalyticsAdapter<Event>()
        val otherEventAdapter = FakeAnalyticsAdapter<OtherEvent>()
        Teller.instance.apply {
            addAdapter(otherEventAdapter)
            addAdapter(eventAdapter)
        }

        val otherEvent = OtherEvent("Other event name")
        Teller.instance.count(otherEvent)

        assertTrue(otherEventAdapter.isInvoked)
        assertFalse(eventAdapter.isInvoked)
    }
}

internal open class FakeAnalyticsAdapter<T> : AnalyticsAdapter<T> {

    var isInvoked = false

    override fun count(event: T) {
        isInvoked = true
    }
}

data class Event(val name: String)
data class OtherEvent(val name: String)
