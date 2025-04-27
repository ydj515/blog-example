package com.example.webfluxwithredisexample.presentation.router.string;

import com.example.webfluxwithredisexample.application.StringAsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StringHandler {
    private final StringAsyncService stringService;

    public Mono<ServerResponse> setString(ServerRequest request) {
        return request.bodyToMono(StringRequest.class)
                .flatMap(stringService::Set)
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> getString(ServerRequest request) {
        String key = request.queryParam("key")
                .orElseThrow(() -> new IllegalArgumentException("Missing query param: key"));
        Mono<StringResponse> response = stringService.Get(key);
        return ServerResponse.ok().body(response, StringResponse.class);
    }

    public Mono<ServerResponse> multiSetString(ServerRequest request) {
        return request.bodyToMono(MultiStringRequest.class)
                .flatMap(stringService::MultiSet)
                .then(ServerResponse.ok().build());
    }
}
