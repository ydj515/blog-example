package com.example.webfluxwithredisexample.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
public class ValueWithTTL<T> {
    T Value;
    Duration TTL;
}

