package com.example.bucket4jexample.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RateLimitTestController {

    @GetMapping("")
    fun defaultApi(): String {
        return "Default API Response"
    }

    @GetMapping("/fast")
    fun fastApi(): String {
        return "Fast API Response"
    }

    @GetMapping("/slow")
    fun slowApi(): String {
        return "Slow API Response"
    }

    @GetMapping("/very-slow")
    fun verySlowApi(): String {
        return "very Slow API Response"
    }

}