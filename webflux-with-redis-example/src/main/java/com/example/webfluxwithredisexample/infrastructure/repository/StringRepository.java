package com.example.webfluxwithredisexample.infrastructure.repository;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class StringRepository {
    private final ReactiveRedisTemplate<String, String> template;
    private final Gson gson;

    @Value("${spring.data.redis.default-time}")
    private Duration defaultExpireTime;

    public <T> Mono<T> getData(String key, Class<T> clazz) {
        return template.opsForValue().get(key)
                .map(json -> gson.fromJson(json, clazz));
    }

    public <T> Mono<Boolean> setData(String key, T value) {
        String jsonValue = gson.toJson(value);
        return template.opsForValue().set(key, jsonValue)
                .then(template.expire(key, defaultExpireTime));
    }

    public <T> Mono<Boolean> multiSetData(Map<String, T> datas) {
        Map<String, String> jsonMap = new HashMap<>();
        datas.forEach((k, v) -> jsonMap.put(k, gson.toJson(v)));
        return template.opsForValue().multiSet(jsonMap);
    }

}

