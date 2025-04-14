package com.example.warmupexample

import com.example.warmupexample.warmup.WarmupRunner
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationContext
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SpringEventHandler(
    private val applicationContext: ApplicationContext,
) {
    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReadyEvent(event: ApplicationReadyEvent?) {
        println("ApplicationReadyEvent!")
        WarmupRunner(applicationContext).run()
//        WarmupRunner(applicationContext).runAsync()
    }
}

