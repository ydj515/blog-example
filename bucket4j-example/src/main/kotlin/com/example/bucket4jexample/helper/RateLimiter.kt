package com.example.bucket4jexample.helper

import io.github.bucket4j.Bucket
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

@Component
class RateLimiter {

    private val rateLimitBuckets = ConcurrentHashMap<String, Bucket>()

    private fun createNewBucket(capacity: Long, refill: Long): Bucket =
        Bucket.builder()
            .addLimit { it.capacity(capacity).refillGreedy(refill, Duration.ofSeconds(1)) }
            .build()

    fun isRequestAllowed(key: String, path: String): Boolean {
        val bucket = rateLimitBuckets.computeIfAbsent(key) {
            when (path) {
                "/api/fast" -> createNewBucket(10, 10)  // 초당 10개 요청 허용
                "/api/slow" -> createNewBucket(3, 3)    // 초당 3개 요청 허용
                "/api/very-slow" -> createNewBucket(1, 1) // 초당 1개 요청 허용
                else -> createNewBucket(5, 5)           // 기본 초당 5개 요청 허용
            }
        }

        return bucket.tryConsume(1)
    }
}