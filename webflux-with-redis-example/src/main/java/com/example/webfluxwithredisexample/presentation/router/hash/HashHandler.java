package com.example.webfluxwithredisexample.presentation.router.hash;

import com.example.webfluxwithredisexample.application.HashAsyncService;
import com.example.webfluxwithredisexample.domain.HashModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HashHandler {
    private final HashAsyncService hashAsyncService;

    public Mono<ServerResponse> putHashes(ServerRequest request) {
        return request.bodyToMono(HashReqeust.class)
                .flatMap(hashAsyncService::putInHash)
                .flatMap(result -> ServerResponse.ok().body(Mono.just("Hash value saved: " + result), String.class));
    }

    public Mono<ServerResponse> getHashes(ServerRequest request) {
        String key = request.queryParam("key").orElse("");
        String field = request.queryParam("field").orElse("");
        return hashAsyncService.getFromHash(key, field, HashModel.class)
                .flatMap(data -> ServerResponse.ok().body(Mono.just(data), HashModel.class));
    }
}