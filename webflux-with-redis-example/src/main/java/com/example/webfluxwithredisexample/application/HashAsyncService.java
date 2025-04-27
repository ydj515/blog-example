package com.example.webfluxwithredisexample.application;

import com.example.webfluxwithredisexample.infrastructure.repository.HashRepository;
import com.example.webfluxwithredisexample.presentation.router.hash.HashReqeust;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HashAsyncService {

    private final HashRepository redis;

    public <T> Mono<Boolean> putInHash(HashReqeust hashReqeust) {

        return redis.putInHash(hashReqeust.baseRequest().key(), hashReqeust.field(), hashReqeust.name());
    }

    public <T> Mono<T> getFromHash(String key, String field, Class<T> clazz) {
        return redis.getFromHash(key, field, clazz);
    }

}
