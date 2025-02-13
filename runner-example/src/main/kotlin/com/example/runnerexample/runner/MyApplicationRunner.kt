package com.example.runnerexample.runner

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class MyApplicationRunner : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        println("ApplicationRunner!")
    }

}