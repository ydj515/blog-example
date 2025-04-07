package com.example.globalcacheexample.presentation.cache

import org.springframework.cache.CacheManager
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/caches")
class CacheController(
    private val cacheManager: CacheManager,
    private val redisTemplate: StringRedisTemplate
) {

    @GetMapping("/redis")
    fun getRedisCacheContents(): Map<String, Any?> {
        val result = mutableMapOf<String, Any?>()

        // Spring Cache의 기본 prefix는 cacheName::key 형식
        for (cacheName in cacheManager.cacheNames) {
            val keys = redisTemplate.keys("$cacheName::*")
            val values = redisTemplate.opsForValue().multiGet(keys)
            val keyValueMap = keys.zip(values ?: listOf()).toMap()

            result[cacheName] = keyValueMap
        }

        return result
    }
}