package com.example.webfluxwithredisexample.application;

import com.example.webfluxwithredisexample.domain.StringModel;
import com.example.webfluxwithredisexample.domain.ValueWithTTL;
import com.example.webfluxwithredisexample.infrastructure.repository.StrategyRepository;
import com.example.webfluxwithredisexample.infrastructure.repository.StringRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLockReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisStrategyService {
    private final StrategyRepository redis;
    private final StringRepository stringRepository;
    private final RedissonReactiveClient redissonReactiveClient;

    public <T> Mono<ValueWithTTL<T>> getValueWithTtl(String key, Class<T> clazz) {
        return redis.getValueWithTtl(key, clazz);
    }

    public Flux<Long> sumTwoKeysAndRenew(String key1, String key2, String resultKey) {
        return redis.sumTwoKeysAndRenew(key1, key2, resultKey);
    }

    public Mono<Void> lockSample() {
        RLockReactive lock = redissonReactiveClient.getLock("sample");

        return lock.tryLock(10, 60, TimeUnit.SECONDS)
                .flatMap(isLocked -> {
                    if (isLocked) {
                        // 락 획득 성공했을 때 할 일
                        return Mono.empty();
                    } else {
                        // 락 획득 실패했을 때 처리할 일
                        return Mono.error(new RuntimeException("Lock not acquired"));
                    }
                })
                .onErrorResume(e -> {
                    // 에러 처리
                    return Mono.error(new RuntimeException("Lock attempt failed", e));
                })
                .then(lock.unlock());
    }

    // 동기
//    public void lockSample() {
//        RLock lock = redissonClient.getLock("sample");
//
//        try {
//            boolean isLocked = lock.tryLock(10, 60, TimeUnit.SECONDS);
//
//            if (isLocked) {
//
//            }
//        } catch (InterruptedException e) {
//
//        }
//
//        lock.unlock();
//    }

    public StringModel PERStrategy(String key) {
        Mono<ValueWithTTL<StringModel>> valueWithTTL = redis.getValueWithTtl(key, StringModel.class);

        if (valueWithTTL != null) {
            asyncPERStrategy(key, valueWithTTL.block().getTTL());

            return valueWithTTL.block().getValue();
        }

        StringModel fromDBData = new StringModel(key, "new db");

        stringRepository.setData(key, fromDBData);

        return fromDBData;
    }


    @Async
    protected void asyncPERStrategy(String key, Duration remainTTL) {

        double probability = calculateProbability(remainTTL);

        Random random = new Random();

        if (random.nextDouble() < probability) {
            StringModel fromDB = new StringModel(key, "db from");
            stringRepository.setData(key, fromDB);
        }

    }

    private double calculateProbability(Duration remainTtl) {
        double base = 0.5;      // 기본 확률
        double decayRate = 0.1; // 감소율 (TTL이 길수록 감소 빠름)

        long remainSeconds = remainTtl.getSeconds(); // Duration을 초 단위로 변환

        return base * Math.exp(-decayRate * remainSeconds);
    }

    public void LuaScript(String key1, String key2, String newKey) {
        redis.sumTwoKeysAndRenew(key1, key2, newKey);
    }
}
