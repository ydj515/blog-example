package com.example.webfluxwithredisexample.infrastructure.repository;

import com.example.webfluxwithredisexample.domain.ValueWithTTL;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class StrategyRepository {
    private final ReactiveRedisTemplate<String, String> template;
    private final Gson gson;

    @Value("${spring.data.redis.default-time}")
    private Duration defaultExpireTime;

    public <T> Mono<ValueWithTTL<T>> getValueWithTtl(String key, Class<T> clazz) {
        return template.opsForValue().get(key)
                .zipWith(template.getExpire(key))
                .map(tuple -> new ValueWithTTL<>(gson.fromJson(tuple.getT1(), clazz), tuple.getT2()));
    }

    // 동기방식으로 위의 코드와 동일
//    public <T> ValueWithTTL<T> GetValueWithTTl(String key, Class<T> clazz) {
//        T value = null;
//        Long ttl = null;
//
//        try {
//            List<Object> results = template.executePipelined(new RedisCallback<Object>() {
//                public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                    StringRedisConnection conn = (StringRedisConnection) connection;
//                    conn.get(key);
//                    conn.ttl(key);
//                    return null;
//                }
//            });
//
//            value = (T) gson.fromJson((String) results.get(0), clazz);
//            ttl = (Long) results.get(1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return new ValueWithTTL<T>(value, ttl);
//    }

    public Flux<Long> sumTwoKeysAndRenew(String key1, String key2, String resultKey) {
        RedisScript<Long> script = RedisScript.of(new ClassPathResource("/lua/newKey.lua"), Long.class);
        return template.execute(script, List.of(key1, key2, resultKey));
    }


}

