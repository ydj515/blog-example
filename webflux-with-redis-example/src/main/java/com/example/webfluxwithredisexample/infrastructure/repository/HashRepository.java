package com.example.webfluxwithredisexample.infrastructure.repository;

import com.example.webfluxwithredisexample.domain.HashModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Repository
public class HashRepository {
    private final ReactiveRedisTemplate<String, String> template;
    private final Gson gson;

    @Value("${spring.data.redis.default-time}")
    private Duration defaultExpireTime;

    public <T> Mono<Boolean> putInHash(String key, String field, T value) {
        return template.opsForHash().put(key, field, gson.toJson(value));
    }

    public <T> Mono<T> getFromHash(String key, String field, Class<T> clazz) {
        return template.opsForHash().get(key, field)
                .map(value -> {
                    try {
                        return gson.fromJson(value.toString(), clazz);
                    } catch (JsonSyntaxException e) {
                        // 만약 JSON이 아니라 그냥 String이면, 직접 감싸서 HashModel로 만들어서 리턴
                        if (clazz.equals(HashModel.class)) {
                            return clazz.cast(new HashModel(value.toString()));
                        }
                        throw e;
                    }
                });
    }

    public Mono<Long> removeFromHash(String key, String field) {
        return template.opsForHash().remove(key, field);
    }

}

