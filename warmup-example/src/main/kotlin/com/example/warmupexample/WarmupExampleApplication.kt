package com.example.warmupexample

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WarmupExampleApplication

fun main(args: Array<String>) {
    // SpringApplication.run(WarmupExampleApplication::class.java, *args)
    // 위와 아래는 동일 코드
    runApplication<WarmupExampleApplication>(*args)
}
