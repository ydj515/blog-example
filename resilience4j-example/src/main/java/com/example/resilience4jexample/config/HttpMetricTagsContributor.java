package com.example.resilience4jexample.config;

//import io.micrometer.core.instrument.Tag;
//import io.micrometer.core.instrument.Tags;
//import org.springframework.boot.actuate.metrics.web.reactive.server.WebFluxTagsContributor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
//@Component
//public class HttpMetricTagsContributor implements WebFluxTagsContributor {
//
//    private static final String API_NAME_TAG = "apiName";
//    private static final String UNKNOWN_URI = "unknown";
//
//    @Override
//    public Iterable<Tag> httpRequestTags(ServerWebExchange exchange, Throwable exception) {
//        String path = exchange.getRequest().getPath().toString();
////        String apiName = extractApiName(path);
//
//        return Tags.of(API_NAME_TAG, path);
//    }
//
//    private String extractApiName(String path) {
//        String[] parts = path.split("/");
//        return (parts.length > 1) ? parts[1] : UNKNOWN_URI;
//    }
//
//}
