package com.example.runnerexample

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RunnerExampleApplication {
    @PostConstruct
    fun postConstruct() {
        println("postConstruct!")
    }
}

fun main(args: Array<String>) {
    runApplication<RunnerExampleApplication>(*args)
}
