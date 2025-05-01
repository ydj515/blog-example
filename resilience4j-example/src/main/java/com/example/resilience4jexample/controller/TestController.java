package com.example.resilience4jexample.controller;

import com.example.resilience4jexample.service.Resilience4jService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final Resilience4jService service;

    @RequestMapping("")
    public String resilience() {
        return service.resilientCall().join();
    }
}
