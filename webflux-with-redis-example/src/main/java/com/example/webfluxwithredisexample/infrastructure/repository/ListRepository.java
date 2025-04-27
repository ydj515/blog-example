package com.example.webfluxwithredisexample.infrastructure.repository;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ListRepository {
    private final ReactiveRedisTemplate<String, String> template;
    private final Gson gson;

    @Value("${spring.data.redis.default-time}")
    private Duration defaultExpireTime;

    public <T> Mono<Long> addToListLeft(String key, T value) {
        return template.opsForList().leftPush(key, gson.toJson(value));
    }

    public <T> Mono<Long> addToListRight(String key, T value) {
        return template.opsForList().rightPush(key, gson.toJson(value));
    }

    public <T> Flux<T> getAllList(String key, Class<T> clazz) {
        return template.opsForList().range(key, 0, -1)
                .map(json -> gson.fromJson(json, clazz));
    }

    public <T> Mono<Long> removeFromList(String key, T value) {
        return template.opsForList().remove(key, 1, gson.toJson(value));
    }

}

