package com.example.webfluxwithredisexample.presentation.router.sortedset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class SortedSetRouter {

    @Bean
    public RouterFunction<ServerResponse> sortedSetRoute(SortedSetHandler handler) {
        return RouterFunctions
                .route(POST("/sorted-set--collection"), handler::setSortedSet)
                .andRoute(GET("/get-sorted-set-by-range"), handler::getSortedSet)
                .andRoute(GET("/get-sorted-set-by-top"), handler::getTopN);
    }
}
