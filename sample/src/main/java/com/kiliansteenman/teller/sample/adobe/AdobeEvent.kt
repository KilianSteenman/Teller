package com.kiliansteenman.teller.sample.adobe

internal data class AdobeAction(
    val name: String,
    val data: Map<String, String> = emptyMap()
)

internal data class AdobeState(
    val name: String,
    val data: Map<String, String> = emptyMap()
)
