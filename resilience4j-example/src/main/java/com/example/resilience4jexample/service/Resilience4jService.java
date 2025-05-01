package com.example.resilience4jexample.service;


import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class Resilience4jService {

    private final CircuitBreaker circuitBreaker;
    private final TimeLimiter timeLimiter;
    private final RateLimiter rateLimiter;
    private final Retry retry;

    public CompletableFuture<String> resilientCall() {
        ScheduledExecutorService timeLimiterScheduler = Executors.newScheduledThreadPool(1);
        ScheduledExecutorService retryScheduler = Executors.newScheduledThreadPool(1);

        Supplier<CompletionStage<String>> originalSupplier = () ->
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(3000); // 타임아웃 유도
                        return "정상 응답";
                    } catch (InterruptedException e) {
                        throw new RuntimeException("중단됨", e);
                    }
                });

        // Retry → RateLimiter → CircuitBreaker → TimeLimiter → 실제 작업
        // 순서의 이유:
        // Retry: 실패할 수 있는 요청을 다시 시도할 수 있도록 제일 바깥에서 감쌉니다.
        // RateLimiter: 전체 요청 빈도를 제한
        // CircuitBreaker: 지속 실패 감지 후 차단
        // TimeLimiter: 요청의 최대 실행 시간 제어


//        RateLimiter.decorateCompletionStage(
//                CircuitBreaker.decorateCompletionStage(
//                        TimeLimiter.decorateCompletionStage(
//                                originalSupplier
//                        )
//                )
//        )


        // TimeLimiter
        Supplier<CompletionStage<String>> timeLimited =
                TimeLimiter.decorateCompletionStage(timeLimiter, timeLimiterScheduler, originalSupplier);

        // CircuitBreaker
        Supplier<CompletionStage<String>> cbWrapped =
                CircuitBreaker.decorateCompletionStage(circuitBreaker, timeLimited);

        // RateLimiter
        Supplier<CompletionStage<String>> rlWrapped =
                RateLimiter.decorateCompletionStage(rateLimiter, cbWrapped);

        // ✅ Retry (마지막으로 wrapping)
        Supplier<CompletionStage<String>> retryWrapped =
                Retry.decorateCompletionStage(retry, retryScheduler, rlWrapped);

        // 실행 및 fallback 처리
        return retryWrapped.get()
                .toCompletableFuture()
                .exceptionally(ex -> "fallback: " + ex.getClass().getSimpleName()
                        + ", cb=" + circuitBreaker.getState()
                        + ", rl=" + rateLimiter.getMetrics().getAvailablePermissions()
                        + ", retry=" + retry.getMetrics().getNumberOfTotalCalls())
                .whenComplete((r, ex) -> {
                    timeLimiterScheduler.shutdown();
                    retryScheduler.shutdown();
                });
    }

}
