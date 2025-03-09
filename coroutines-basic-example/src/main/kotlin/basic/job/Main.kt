package basic.job

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import basic.printWithThread
import kotlin.system.measureTimeMillis

//[main] 3
//[main] 소요시간 : 2017
// Lazy를 사용하면 await()을 호출했을 때 계산 결과를 계속 기다린다.
fun example9(): Unit = runBlocking {
    val time = measureTimeMillis {
        val job1 = async(start = CoroutineStart.LAZY) { apiCall1() }
        val job2 = async(start = CoroutineStart.LAZY) { apiCall2Int(job1.await()) }
        printWithThread(job2.await())
    }

    printWithThread("소요시간 : $time")

}

//[main] 3
//[main] 소요시간 : 2019
fun example8(): Unit = runBlocking {
    val time = measureTimeMillis {
        val job1 = async { apiCall1() }
        val job2 = async { apiCall2Int(job1.await()) }
        printWithThread(job2.await())
    }

    printWithThread("소요시간 : $time")

}

suspend fun apiCall1Int(): Int {
    delay(1_000L)
    return 1
}

suspend fun apiCall2Int(num: Int): Int {
    delay(1_000L)
    return 2 + num
}

//[main] 3
//[main] 소요시간 : 1015
fun example7(): Unit = runBlocking {
    val time = measureTimeMillis {
        val job1 = async { apiCall1() }
        val job2 = async { apiCall2() }
        printWithThread(job1.await() + job2.await())
    }

    printWithThread("소요시간 : $time")

}

suspend fun apiCall1(): Int {
    delay(1_000L)
    return 1
}

suspend fun apiCall2(): Int {
    delay(1_000L)
    return 2
}

//[main] 8
fun example6(): Unit = runBlocking {
    val job = async {
        3 + 5
    }
    val result = job.await() // await : async의 결과를 가져옴
    printWithThread(result)
}


//[main] job 1
//[main] 소요시간 : 1014
//[main] job 2
fun example5(): Unit = runBlocking {
    val time = measureTimeMillis {
        val job1 = launch {
            delay(1_000L)
            printWithThread("job 1")
        }
        job1.join()
        val job2 = launch {
            delay(1_000L)
            printWithThread("job 2")
        }
    }

    printWithThread("소요시간 : $time")

}


//[main] 소요시간 : 2
//[main] job 1
//[main] job 2
fun example4(): Unit = runBlocking {
    val time = measureTimeMillis {
        val job1 = launch {
            delay(1_000L)
            printWithThread("job 1")
        }

        val job2 = launch {
            delay(1_000L)
            printWithThread("job 2")
        }
    }
    printWithThread("소요시간 : $time")
}


fun example3(): Unit = runBlocking {
    val job = launch {
        (1..5).forEach {
            printWithThread("Routine $it")
            delay(500)
        }
    }

    delay(1_000L)
    job.cancel()
}


fun example2(): Unit = runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {
        printWithThread("hello launch")
    }

    job.start()
}


fun example1() {
    runBlocking {
        printWithThread("Start")
        launch {
            delay(2_000L) // 특정시간만큼 멈추고 다른 코루틴에게 넘김
            printWithThread("Launch end")
        }
    }

    printWithThread("End")
}