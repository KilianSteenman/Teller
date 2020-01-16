package com.kiliansteenman.teller

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TellerTest {

    @Test(expected = IllegalStateException::class)
    fun `when no adapter is registered for type, then invalid state exception is thrown`() {
        val teller = createTeller()

        val event = Event("name")
        teller.count(event)
    }

    @Test
    fun `when adapter is registered for type, then adapter is used`() {
        val eventAdapter = FakeAnalyticsAdapter<Event>()
        val teller = createTeller().apply {
            addAdapter(eventAdapter)
        }

        val event = Event("This an event name")
        teller.count(event)

        assertTrue(eventAdapter.isInvoked)
    }

    @Test
    fun `when multiple adapters are registered for different types, the correct adapter is used`() {
        val eventAdapter = FakeAnalyticsAdapter<Event>()
        val otherEventAdapter = FakeAnalyticsAdapter<OtherEvent>()
        val teller = createTeller().apply {
            addAdapter(otherEventAdapter)
            addAdapter(eventAdapter)
        }

        val otherEvent = OtherEvent("Other event name")
        teller.count(otherEvent)

        assertTrue(otherEventAdapter.isInvoked)
        assertFalse(eventAdapter.isInvoked)
    }

    private fun createTeller(): Teller {
        return Teller()
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