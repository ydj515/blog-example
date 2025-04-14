package com.example.localcacheexample.presentation.cache

data class CacheStat(
    val hitCount: Long,
    val missCount: Long,
    val hitRate: Double,
    val evictionCount: Long,
    val loadSuccessCount: Long,
    val loadFailureCount: Long
)
