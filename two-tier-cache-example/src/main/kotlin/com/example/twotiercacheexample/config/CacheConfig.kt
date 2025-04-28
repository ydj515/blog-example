package com.example.twotiercacheexample.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
//@EnableCaching
class CacheConfig {

    @Bean
    fun caffeineCacheManager(): CaffeineCacheManager {
        val manager = CaffeineCacheManager()
        manager.setCaffeine(
            Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(1000)
        )
        return manager
    }

    @Bean
    fun redisObjectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            // 타입 정보 비활성화
            deactivateDefaultTyping()

            // 추가 설정들
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        }

    }

    @Bean
    fun redisCacheManager(
        redisConnectionFactory: RedisConnectionFactory,
        redisObjectMapper: ObjectMapper
    ): RedisCacheManager {
        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(
                RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(30))
                    .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer())
                    )
                    .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                            GenericJackson2JsonRedisSerializer(redisObjectMapper)
                        )
                    )
            )
            .build()
    }

    @Bean
    fun redisTemplate(
        redisConnectionFactory: RedisConnectionFactory,
        redisObjectMapper: ObjectMapper
    ): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = redisConnectionFactory

        val keySerializer = StringRedisSerializer()
        val valueSerializer = Jackson2JsonRedisSerializer(redisObjectMapper, Any::class.java)

        template.keySerializer = keySerializer
        template.valueSerializer = valueSerializer
        template.hashKeySerializer = keySerializer
        template.hashValueSerializer = valueSerializer

        template.afterPropertiesSet()
        return template
    }
}