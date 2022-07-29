package com.kiliansteenman.teller.logger.data

data class TellerLog(
    val id: Long = 0,
    val framework: String,
    val type: String,
    val logDate: Long = System.currentTimeMillis(),
    val title: String,
    val params: Map<String, String>,
)