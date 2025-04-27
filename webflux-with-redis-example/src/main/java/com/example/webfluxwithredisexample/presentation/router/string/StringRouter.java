package com.example.webfluxwithredisexample.presentation.router.string;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class StringRouter {

    @Bean
    public RouterFunction<ServerResponse> route(StringHandler handler) {
        return RouterFunctions
                .route(POST("/set-string-collection"), handler::setString)
                .andRoute(GET("/get-string-collection"), handler::getString)
                .andRoute(POST("/multi-set-collection"), handler::multiSetString);
    }
}
