package basic.suspend

import basic.printWithThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

// deferred의 의존성 존재.
fun main(): Unit = runBlocking{
    val result1 = async {
        call1()
    }
    val result2 = async {
        call2(result1.await())
    }

    printWithThread(result2.await())
}

fun call1(): Int {
    Thread.sleep(1000)
    return 100
}

fun call2(num: Int): Int {
    Thread.sleep(1000)
    return num * 2
}