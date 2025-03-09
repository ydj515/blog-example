package basic.suspend

import basic.printWithThread
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull


fun example2(): Unit = runBlocking {
    val result: Int? = withTimeoutOrNull(1000L) {
        delay(1500)
        10 + 20
    }

    printWithThread(result!!)
}

// Exception in thread "main" kotlinx.coroutines.TimeoutCancellationException: Timed out waiting for 1000 ms
fun example1(): Unit = runBlocking {
    val result: Int = withTimeout(1000L) {
        delay(1500)
        10 + 20
    }

    printWithThread(result)
}