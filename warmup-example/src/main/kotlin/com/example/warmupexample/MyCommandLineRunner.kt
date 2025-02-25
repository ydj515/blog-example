package com.example.warmupexample

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class MyCommandLineRunner : CommandLineRunner {
    override fun run(vararg args: String?) {
        println("CommandLineRunner!")
    }

}