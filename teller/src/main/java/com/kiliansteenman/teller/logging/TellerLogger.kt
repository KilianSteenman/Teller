package com.kiliansteenman.teller.logging

interface TellerLogger {

    fun <T : Any> log(event: T)
}