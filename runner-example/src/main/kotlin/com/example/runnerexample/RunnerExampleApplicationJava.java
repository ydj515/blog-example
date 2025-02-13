package com.example.runnerexample;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunnerExampleApplicationJava {


    public static void main(String[] args) {
        SpringApplication.run(RunnerExampleApplicationJava.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("post construct!");
    }

}
