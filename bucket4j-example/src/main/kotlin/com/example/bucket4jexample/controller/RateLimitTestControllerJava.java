package com.example.bucket4jexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class RateLimitTestControllerJava {

    @GetMapping("")
    public String defaultApi() {
        return "Default API Response";
    }

    @GetMapping("/fast")
    public String fastApi() {
        return "Fast API Response";
    }

    @GetMapping("/slow")
    public String slowApi() {
        return "Slow API Response";
    }

    @GetMapping("/very-slow")
    public String verySlowApi() {
        return "very Slow API Response";
    }
}
