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
                .timeoutDuration(Duration.ofSeconds(2))       // 타임아웃 제한 시간 (2초)
                .cancelRunningFuture(true)                    // 타임아웃 시 실행 중인 작업을 취소
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
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)  // 호출 수 기반으로 실패율 측정
                .failureRateThreshold(50)                                              // 실패율 50% 이상이면 차단
                .waitDurationInOpenState(Duration.ofSeconds(10))                        // OPEN 상태 유지 시간 (10초)
                .slidingWindowSize(10)                                                 // 모니터링할 호출 수 (10개)
                .permittedNumberOfCallsInHalfOpenState(3)                               // HALF_OPEN 상태에서 허용할 호출 수 (3개)
                .minimumNumberOfCalls(5)                                               // 실패율 계산을 시작할 최소 호출 수 (5개)
                .recordExceptions(Throwable.class)                                      // 모든 예외를 실패로 간주
                .build();

        // 커스텀 config 기반 Registry 생성
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(circuitBreakerConfig);

        // Micrometer 메트릭 연동 (Actuator + Prometheus 등과 연결 시 유용)
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
                .limitForPeriod(5)                                // 주기(10초)당 최대 호출 횟수 (5회)
                .limitRefreshPeriod(Duration.ofSeconds(10))       // 주기 시간 (10초)
                .timeoutDuration(Duration.ofMillis(500))          // 초과 시 최대 대기 시간 (500ms)
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
                .maxAttempts(3)                               // 최대 3회 시도 (최초 + 재시도 2회)
                .waitDuration(Duration.ofMillis(500))         // 각 재시도 간 대기 시간 (500ms)
//              .retryExceptions(RuntimeException.class)      // (선택) 특정 예외만 재시도하도록 설정 가능
                .retryExceptions(Throwable.class)             // 모든 Throwable에 대해 재시도 수행
                .build();

        return RetryRegistry.of(config);
    }

    @Bean
    public Retry customRetry(RetryRegistry registry) {
        return registry.retry("customRetry");
    }
}
