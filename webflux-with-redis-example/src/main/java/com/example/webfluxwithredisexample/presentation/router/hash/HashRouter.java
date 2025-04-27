package com.example.webfluxwithredisexample.presentation.router.hash;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class HashRouter {

    @Bean
    public RouterFunction<ServerResponse> hashRoute(HashHandler handler) {
        return RouterFunctions
                .route(POST("/put-hashes"), handler::putHashes)
                .andRoute(GET("/get-hash-value"), handler::getHashes);
    }
}
