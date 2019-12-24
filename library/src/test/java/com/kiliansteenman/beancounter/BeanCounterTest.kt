package com.kiliansteenman.beancounter

import org.junit.Test

class BeanCounterTest {

    @Test(expected = IllegalArgumentException::class)
    fun `when no adapter is registered for type, then invalid state exception is thrown`() {
        val beanCounter = createBeanCounter()

        val event = Event("name")

        beanCounter.count(event)
    }

    @Test
    fun `when adapter is registered for type, then adapter is used`() {
        val beanCounter = createBeanCounter()

        beanCounter.addAdapter(eventAdapter)
        beanCounter.addAdapter(omnitureEventAdapter)

        val event = Event("This an event name")
        val omnitureEvent = OmnitureEvent("Hello Omniture!")

        beanCounter.count(event)
        beanCounter.count(omnitureEvent)

//        verify(adapter).count(event)
    }

    private fun createBeanCounter(): BeanCounter {
        return BeanCounter()
    }

    companion object {

        private val eventAdapter = object : AnalyticsAdapter<Event> {
            override fun count(event: Event) {
                println("Counting event: ${event.name}")
            }
        }

        private val omnitureEventAdapter = object : AnalyticsAdapter<OmnitureEvent> {
            override fun count(event: OmnitureEvent) {
                println("Counting Omniture: ${event.name}")
            }
        }
    }
}

data class Event(val name: String)
data class OmnitureEvent(val name: String)