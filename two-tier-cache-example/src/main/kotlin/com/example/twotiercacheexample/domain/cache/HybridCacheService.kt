package com.example.twotiercacheexample.domain.cache

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.cache.CacheException
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.io.Serializable
import java.time.Duration

@Component
class HybridCacheService(
    private val caffeineCacheManager: CaffeineCacheManager,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val redisObjectMapper: ObjectMapper,
) {

    fun <T> get(key: String, typeReference: TypeReference<T>, loader: () -> T): T {
        val caffeineCache = caffeineCacheManager.getCache(key)

        // 1. 로컬(Caffeine) 캐시 조회
        val localCacheValue = caffeineCache?.get(key)?.get()

        if (localCacheValue != null) {
            return redisObjectMapper.convertValue(localCacheValue, typeReference)
        }


        // 2. 로컬에 없으면 Redis 조회
        val remoteCacheValue = redisTemplate.opsForValue().get(key)
        if (remoteCacheValue != null) {
            val jsonString = redisObjectMapper.writeValueAsString(remoteCacheValue)
            val value = redisObjectMapper.readValue(jsonString, typeReference)
            caffeineCache?.put(key, value)
            return value
        }


        // 3. 둘 다 없으면 loader 호출 (DB 조회 등)하고 저장
        val loaded = loader()
        save(key, loaded as Serializable)
        return loaded
    }

    fun <T : Serializable> save(key: String, value: T) {
        try {
            val caffeineCache = caffeineCacheManager.getCache(key)
                ?: throw CacheException("Caffeine cache not found")

            // 트랜잭션적 접근
            try {
                caffeineCache.put(key, value) // local cache save

                redisTemplate.opsForValue().set(key, value, Duration.ofHours(1)) // global cache save & TTL 설정
            } catch (e: Exception) {
                // 롤백: Caffeine에서 제거
                caffeineCache.evict(key)
                throw CacheException("Failed to save to cache", e)
            }
        } catch (e: Exception) {
            throw CacheException("Cache operation failed", e)
        }
    }

    fun evict(key: String) {
        // 캐시를 "지우기" (삭제)
        // 데이터 일관성을 보장할 수 있지만 성능에 약간의 부담이 있을 수 있습니다.
        // 데이터 변경이 자주 발생하고, 정확한 데이터 일관성이 중요한 경우
        val caffeineCache = caffeineCacheManager.getCache("yourCacheName")
        caffeineCache?.evict(key)
        redisTemplate.delete(key)

        // 만약 갱신을 원한다면, 아래와 같이 갱신도 가능
        // 성능상 이점은 있지만, 데이터가 불일치 상태일 가능성도 있습니다.
        // 데이터의 변경 빈도가 낮고, 효율성이 더 중요한 경우에 사용
        // save(key, loaded) // 이 방법은 데이터 갱신이 필요할 때 사용
    }
}