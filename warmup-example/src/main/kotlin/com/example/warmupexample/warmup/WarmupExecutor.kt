package com.example.warmupexample.warmup

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.system.measureTimeMillis

// 비동기 코루틴으로 warmup 대상 메서드 실행 + 시간 측정 + 예외 처리
class WarmupExecutor {
    suspend fun executeWarmups(warmups: List<WarmupScanner.WarmupTarget>) = coroutineScope {
        val jobs = warmups.map { warmup ->
            async {
                val time = measureTimeMillis {
                    try {
                        println("Executing: ${warmup.bean::class.simpleName}.${warmup.methodName}")
                        warmup.method()
                        println("Success: ${warmup.methodName}")
                    } catch (e: Exception) {
                        println("Failed: ${warmup.methodName} - ${e.message}")
                    }
                }
                println("${warmup.methodName} took ${time}ms")
            }
        }
        jobs.forEach { it.await() }
    }
}