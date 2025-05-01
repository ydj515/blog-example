package com.example.resilience4jexample.service;


import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TimeLimitDemoServiceTest {

    @Test
    void timeLimiter_should_trigger_timeout_fallback() {
        // given: TimeLimiterRegistry 설정 (timeout 2초)
        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(2))
                .cancelRunningFuture(true)
                .build();

        TimeLimiterRegistry registry = TimeLimiterRegistry.of(config);
        TimeLimitDemoService service = new TimeLimitDemoService(registry);

        // when
        String result = service.callWithTimeout();

        // then
        assertThat(result).isEqualTo("타임아웃: fallback 응답");
    }
}