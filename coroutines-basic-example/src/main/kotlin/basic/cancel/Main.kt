package basic.cancel

import basic.printWithThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

//[main] 취소시작
//[main] delay에 의해 취소되지않음
fun main(): Unit = runBlocking {
    val job = launch {
        try {
            delay(1000L)
        } catch (e: CancellationException) {
            // 여기서 throw를 하지않으면 안된다.
        }

        printWithThread("delay에 의해 취소되지않음")
    }

    delay(100L)
    printWithThread("취소시작")
    job.cancel()
}


// 코루틴 정상 취소
//[DefaultDispatcher-worker-1] 1번째 호출
fun example4(): Unit = runBlocking {
    // Dispatchers.Default : 다른 스레드에서 launch안의 코드가 돌게 만듦
    val job = launch(Dispatchers.Default) {
        var i = 1
        var nextPrintTime = System.currentTimeMillis()
        while (i <= 5) {
            if (nextPrintTime <= System.currentTimeMillis()) {
                printWithThread("${i++}번째 호출")
                nextPrintTime += 1_000L
            }
            if(!isActive) {
                throw CancellationException()
            }
        }
    }

    delay(100L)
    job.cancel()
}

// 코루틴 취소가 안됨.
//[main] 1번째 호출
//[main] 2번째 호출
//[main] 3번째 호출
//[main] 4번째 호출
//[main] 5번째 호출
fun example3(): Unit = runBlocking {
    val job = launch {
        var i = 1
        var nextPrintTime = System.currentTimeMillis()
        while (i <= 5) {
            if (nextPrintTime <= System.currentTimeMillis()) {
                printWithThread("${i++}번째 호출")
                nextPrintTime += 1_000L
            }
        }
    }

    delay(100L)
    job.cancel()
}

// [main] job 1
fun example2(): Unit = runBlocking {
    val job1 = launch {
        delay(10)
        printWithThread("job 1")
    }

    delay(100)
    job1.cancel()
}

// [main] job 2
fun example1(): Unit = runBlocking {
    val job1 = launch {
        delay(1_000L)
        printWithThread("job 1")
    }

    val job2 = launch {
        delay(1_000L)
        printWithThread("job 2")
    }

    delay(100)
    job1.cancel()
}