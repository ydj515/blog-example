package com.example.webfluxwithredisexample.application;

import com.example.webfluxwithredisexample.infrastructure.repository.ListRepository;
import com.example.webfluxwithredisexample.presentation.router.list.ListRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ListAsyncService {
    private final ListRepository redis;

    public <T> Mono<Long> addToListLeft(ListRequest req) {
        return redis.addToListLeft(req.baseRequest().key(), req.name());
    }

    public <T> Mono<Long> addToListRight(ListRequest req) {
        return redis.addToListRight(req.baseRequest().key(), req.name());
    }

    public <T> Flux<T> getAllList(String key, Class<T> clazz) {
        return redis.getAllList(key, clazz);
    }

    public <T> Mono<Long> removeFromList(String key, T value) {
        return redis.removeFromList(key, value);
    }
}
