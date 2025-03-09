package basic.scope

import basic.printWithThread
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class Main {
}


fun main() {
    // 스레드풀을 만들고 여러 코루틴을 스레드 풀안에서 수행 가능
    CoroutineName("my coroutine") + Dispatchers.Default
    val threadPool = Executors.newSingleThreadExecutor()

    CoroutineScope(threadPool.asCoroutineDispatcher()).launch {
        printWithThread("new coroutine")
    }
}

class AsyncLogic {
    private val scope = CoroutineScope(Dispatchers.Default)

    fun doSomething() {
        scope.launch {
            // do something
        }
    }

    fun destroy() {
        scope.cancel() // scope안의 모든 코루틴 취소 signal
    }
}
