package com.example.warmupexample.warmup

import kotlinx.coroutines.runBlocking
import org.springframework.context.ApplicationContext

// WarmupScanner -> WarmupExecutor 조합하여 실행 흐름 관리
class WarmupRunner(
    private val applicationContext: ApplicationContext
) {
    fun run() = runBlocking {
        val scanner = WarmupScanner(applicationContext)
        val executor = WarmupExecutor()

        println("Start warmup tasks...")
        val warmups = scanner.scan()
        executor.executeWarmups(warmups)
        println("All warmup tasks completed.")
    }
}