package com.example.runnerexample.eventhandler

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SpringEventHandler {
    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReadyEvent(event: ApplicationReadyEvent?) {
        println("ApplicationReadyEvent!")
    }
}

