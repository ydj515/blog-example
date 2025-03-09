package basic.structuredConcurrency

import basic.printWithThread
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 바로 에러 발생.
// 자식 코루틴을 기다리다가 예외가 발생하면 예외가 부모로 전파되고 다른 자식 코루틴에게 취소 요청을 보낸다.
fun main(): Unit = runBlocking {
    // 자식1
    launch {
        delay(600L)
        printWithThread("A")
    }

    // 자식2
    launch {
        delay(500L)
        throw IllegalArgumentException("코루틴 실패!")
    }
}

// 자식 1은 수행이 되고 자식 2가 수행될 때 에러가 발생해 자식2의 에러가 부모 코루틴에게 전파됨.
fun example1(): Unit = runBlocking {
    // 자식1
    launch {
        delay(500L)
        printWithThread("A")
    }

    // 자식2
    launch {
        delay(600L)
        throw IllegalArgumentException("코루틴 실패!")
    }
}