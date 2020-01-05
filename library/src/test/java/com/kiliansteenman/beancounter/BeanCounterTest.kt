package com.kiliansteenman.beancounter

import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BeanCounterTest {

    @After
    fun after() {
        eventAdapter.isInvoked = false
        otherEventAdapter.isInvoked = false
    }

    @Test(expected = IllegalStateException::class)
    fun `when no adapter is registered for type, then invalid state exception is thrown`() {
        val beanCounter = createBeanCounter()

        val event = Event("name")
        beanCounter.count(event)
    }

    @Test
    fun `when adapter is registered for type, then adapter is used`() {
        val beanCounter = createBeanCounter().apply {
            addAdapter(eventAdapter)
        }

        val event = Event("This an event name")
        beanCounter.count(event)

        assertTrue(eventAdapter.isInvoked)
    }

    @Test
    fun `when multiple adapters are registered for different types, the correct adapter is used`() {
        val beanCounter = createBeanCounter().apply {
            addAdapter(otherEventAdapter)
            addAdapter(eventAdapter)
        }

        val otherEvent = OtherEvent("Other event name")
        beanCounter.count(otherEvent)

        assertTrue(otherEventAdapter.isInvoked)
        assertFalse(eventAdapter.isInvoked)
    }

    private fun createBeanCounter(): BeanCounter {
        return BeanCounter()
    }

    companion object {

        private val eventAdapter = object : AnalyticsAdapter<Event> {

            var isInvoked = false

            override fun count(event: Event) {
                isInvoked = true
            }
        }

        private val otherEventAdapter = object : AnalyticsAdapter<OtherEvent> {

            var isInvoked = false

            override fun count(event: OtherEvent) {
                isInvoked = true
            }
        }
    }
}

data class Event(val name: String)
data class OtherEvent(val name: String)