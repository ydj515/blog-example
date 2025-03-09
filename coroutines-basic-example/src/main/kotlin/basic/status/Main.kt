package basic.status

import basic.newRoutine
import basic.printWithThread
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException

fun main(): Unit = runBlocking {

    // CoroutineExceptionHandler : launch에만 적용 가능. 부모 코루틴이 있으면 동작하지않음
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        printWithThread("예외")
        throw throwable
    }


    val job = CoroutineScope(Dispatchers.Default).launch(exceptionHandler) {
        throw IllegalArgumentException()
    }

    delay(1_000L)
}

// 바로 에러가 출력되지않음.
fun example6(): Unit = runBlocking {
    val job = async(SupervisorJob()) { // SupervisorJob: 자식 코루틴의 예외를 전파하지않음.
        throw IllegalArgumentException()
    }
    delay(1_000L)
}

// 바로 에러가 발생함.
// 자식 코루틴의 에러가 전파되기 때문.
// Exception in thread "main" java.lang.IllegalArgumentException
fun example5(): Unit = runBlocking {
    val job = async { // 자식 코루틴
        throw IllegalArgumentException()
    }
    delay(1_000L)
}

// Exception in thread "main" java.lang.IllegalArgumentException
fun example4(): Unit = runBlocking {

    // maint thread가 아닌 다른 thread에서 root 코루틴을 생성한다.
    val job = CoroutineScope(Dispatchers.Default).async {

        throw IllegalArgumentException()
    }
    delay(1_000L)
    job.await() // 여기서 exception을 잡을 수 있음.(main thread에서 잡을 것임)
}


// 예외가 발생되지않음
fun example3(): Unit = runBlocking {

    // maint thread가 아닌 다른 thread에서 root 코루틴을 생성한다.
    val job = CoroutineScope(Dispatchers.Default).async {

        throw IllegalArgumentException()
    }
    delay(1_000L)
}


//Exception in thread "DefaultDispatcher-worker-1" java.lang.IllegalArgumentException
fun example2(): Unit = runBlocking {

    // maint thread가 아닌 다른 thread에서 root 코루틴을 생성한다.
    val job = CoroutineScope(Dispatchers.Default).launch {

        throw IllegalArgumentException()
    }
    delay(1_000L)
}


//[DefaultDispatcher-worker-2] job 1
//[main] job 2
fun example1(): Unit = runBlocking {

    // maint thread가 아닌 다른 thread에서 root 코루틴을 생성한다.
    val job1 = CoroutineScope(Dispatchers.Default).launch {
        delay(1_000L)
        printWithThread("job 1")
    }

    val job2 = launch {
        delay(1_000L)
        printWithThread("job 2")
    }
}