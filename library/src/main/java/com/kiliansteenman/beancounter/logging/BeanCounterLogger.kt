package com.kiliansteenman.beancounter.logging

interface BeanCounterLogger {

    fun <T : Any> log(event: T)
}