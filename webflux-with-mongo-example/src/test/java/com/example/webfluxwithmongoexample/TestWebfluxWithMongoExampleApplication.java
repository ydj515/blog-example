package com.example.webfluxwithmongoexample;

import org.springframework.boot.SpringApplication;

public class TestWebfluxWithMongoExampleApplication {

    public static void main(String[] args) {
        SpringApplication.from(WebfluxWithMongoExampleApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
