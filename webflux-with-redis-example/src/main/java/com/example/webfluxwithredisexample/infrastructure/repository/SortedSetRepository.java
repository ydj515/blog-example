package com.example.webfluxwithredisexample.infrastructure.repository;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Repository
public class SortedSetRepository {
    private final ReactiveRedisTemplate<String, String> template;
    private final Gson gson;

    @Value("${spring.data.redis.default-time}")
    private Duration defaultExpireTime;

    public <T> Mono<Boolean> addToSortedSet(String key, T value, Double score) {
        String jsonValue = gson.toJson(value);
        return template.opsForZSet().add(key, jsonValue, score);
    }

    public <T> Flux<T> rangeByScore(String key, double minScore, double maxScore, Class<T> clazz) {
        return template.opsForZSet().rangeByScore(key, Range.closed(minScore, maxScore))
                .map(json -> gson.fromJson(json, clazz));
    }

    public <T> Flux<T> getTopNFromSortedSet(String key, int n, Class<T> clazz) {
        return template.opsForZSet().reverseRange(key, Range.closed(0L, (long) n - 1))
                .map(json -> gson.fromJson(json, clazz));
    }

}

