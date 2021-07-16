package com.kiliansteenman.teller.sample.adobe

internal sealed class AdobeEvent(
    open val name: String,
    open val data: Map<String, String> = emptyMap()
) {
    data class Action(
        override val name: String,
        override val data: Map<String, String>
    ) : AdobeEvent(name, data)

    data class State(
        override val name: String,
        override val data: Map<String, String>
    ) : AdobeEvent(name, data)
}
