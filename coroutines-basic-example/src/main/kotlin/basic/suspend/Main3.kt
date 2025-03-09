package basic.suspend

import basic.printWithThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CompletableFuture

// deferred의 의존성 사라짐
fun main(): Unit = runBlocking{

    // 반환타입이 deferred가 아니게 되므로 어떤 비동기 라이브러리가 와도 무방하게 됨.
    val result1 = suspendCall1()

    val result2 = suspendCall2(result1)

    printWithThread(result2)
}

suspend fun suspendCall1(): Int {
    return CoroutineScope(Dispatchers.Default).async {
        Thread.sleep(1000)
        100
    }.await()
}

suspend fun suspendCall2(num: Int): Int {
    return CompletableFuture.supplyAsync {
        Thread.sleep(1000)
        num * 2
    }.await()
}