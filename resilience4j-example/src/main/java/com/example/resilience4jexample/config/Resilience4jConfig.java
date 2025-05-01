package com.example.resilience4jexample.config;


import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.micrometer.tagged.TaggedCircuitBreakerMetrics;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public TimeLimiterRegistry timeLimiterRegistry() {
        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(2))
                .cancelRunningFuture(true)
                .build();

        return TimeLimiterRegistry.of(config);
    }

    @Bean
    public TimeLimiter customTimeLimiter(TimeLimiterRegistry registry) {
        // "customLimiter"라는 이름으로 TimeLimiter 인스턴스 생성
        return registry.timeLimiter("customLimiter");
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(MeterRegistry meterRegistry) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .slidingWindowSize(10)
                .permittedNumberOfCallsInHalfOpenState(3)
                .minimumNumberOfCalls(5)
                .recordExceptions(Throwable.class)
                .build();

        // 커스텀 config 기반 Registry 생성
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(circuitBreakerConfig);

        // Micrometer 연동
        TaggedCircuitBreakerMetrics.ofCircuitBreakerRegistry(registry).bindTo(meterRegistry);

        return registry;
    }

    @Bean
    public CircuitBreaker customCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("customBreaker");
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(5)  // 주기당 최대 호출 횟수
                .limitRefreshPeriod(Duration.ofSeconds(10))  // 주기 시간
                .timeoutDuration(Duration.ofMillis(500))    // 기다리는 시간
                .build();

        return RateLimiterRegistry.of(config);
    }

    @Bean
    public RateLimiter customRateLimiter(RateLimiterRegistry registry) {
        return registry.rateLimiter("customLimiter");
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)                           // 최대 3회 시도 (최초 + 재시도 2회)
                .waitDuration(Duration.ofMillis(500))     // 각 재시도 사이의 대기 시간
//                .retryExceptions(RuntimeException.class)  // 어떤 예외를 리트라이할지
                .retryExceptions(Throwable.class)
                .build();

        return RetryRegistry.of(config);
    }

    @Bean
    public Retry customRetry(RetryRegistry registry) {
        return registry.retry("customRetry");
    }
}
