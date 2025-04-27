package com.example.webfluxwithredisexample.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SortedSetModel {
    String name;
    Double score;
}
