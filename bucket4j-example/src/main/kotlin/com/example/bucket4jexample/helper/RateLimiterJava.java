package com.example.bucket4jexample.helper;

import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class RateLimiterJava {

    private final ConcurrentMap<String, Bucket> rateLimitBuckets = new ConcurrentHashMap<>();

    private Bucket createNewBucket(long capacity, long refill) {
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(capacity).refillGreedy(refill, Duration.ofSeconds(1)))
                .build();
    }

    public boolean isRequestAllowed(String key, String path) {
        Bucket bucket = rateLimitBuckets.computeIfAbsent(key, v -> switch (path) {
            case "/api/fast" -> createNewBucket(10, 10);  // 초당 10개 요청 허용
            case "/api/slow" -> createNewBucket(3, 3);    // 초당 3개 요청 허용
            case "/api/very-slow" -> createNewBucket(1, 1); // 초당 1개 요청 허용
            default -> createNewBucket(5, 5);            // 기본 초당 5개 요청 허용
        });

        return bucket.tryConsume(1);
    }
}
