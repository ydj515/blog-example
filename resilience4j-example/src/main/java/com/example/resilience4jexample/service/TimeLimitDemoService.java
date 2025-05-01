package com.example.resilience4jexample.service;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class TimeLimitDemoService {

    private final TimeLimiterRegistry timeLimiterRegistry;

    public String callWithTimeout() {
        TimeLimiter timeLimiter = timeLimiterRegistry.timeLimiter("customLimiter");

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<String> task = () -> {
            Thread.sleep(3000); // 3초 걸리는 작업
            return "성공 응답";
        };

        try {
            return timeLimiter.executeFutureSupplier(() ->
                    CompletableFuture.supplyAsync(() -> {
                        try {
                            return task.call();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }, executor)
            );
        } catch (Exception e) {
            return "타임아웃: fallback 응답";
        } finally {
            executor.shutdown();
        }
    }
}