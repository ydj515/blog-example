
package com.example.warmupexample.warmup

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

// 비동기 코루틴으로 warmup 대상 메서드 실행 + 시간 측정 + 예외 처리
class WarmupExecutor {
    // 실행은 비동기적이지만, 전체 warmup이 끝날 때까지 대기하는 구조
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

    // 실행도 및 전체 warmup이 끝날 때까지 대기하지 않음
    fun executeWarmupsAsync(warmups: List<WarmupScanner.WarmupTarget>) {
        GlobalScope.launch {
            warmups.forEach { warmup ->
                launch {
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
        }
    }
}