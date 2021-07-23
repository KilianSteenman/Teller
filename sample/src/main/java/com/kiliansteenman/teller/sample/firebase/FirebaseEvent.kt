package com.kiliansteenman.teller.sample.firebase

internal data class FirebaseEvent(
    val name: String,
    val params: Map<String, String> = emptyMap()
)