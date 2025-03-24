package com.example.webfluxwithmongoexample.presentation.router.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {
    private String name;
    private int age;
}
