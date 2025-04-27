package com.example.twotiercacheexample.presentation.cache

import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/local-caches")
class LocalCacheController(
    private val cacheManager: CaffeineCacheManager,
) {

    @GetMapping("")
    // TTL 만료 후 조회해보면 key가 있는 것 처럼 보인다. Caffeine은 lazy eviction 방식이기 때문.
    fun getAllCacheContents(): Map<String, Map<Any, Any>> {
        val result = mutableMapOf<String, Map<Any, Any>>()

        for (cacheName in cacheManager.cacheNames) {
            val nativeCache = cacheManager.getCache(cacheName)?.nativeCache

            if (nativeCache is com.github.benmanes.caffeine.cache.Cache<*, *>) {
                val mapView = nativeCache.asMap()
                result[cacheName] = mapView.mapKeys { it.key!! }.mapValues { it.value!! }
            } else {
                result[cacheName] = mapOf("unsupported" to "Not a Caffeine cache")
            }
        }

        return result
    }
}