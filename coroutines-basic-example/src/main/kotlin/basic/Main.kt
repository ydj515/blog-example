package basic

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield


fun main(): Unit = runBlocking { // runBlocking에 의해 coroutine이 하나 생성됨.
    printWithThread("Start")
    launch { // 반환값이 없는 코루틴을 만든다. 새롱 생기는 코루틴을 바로 실행하지않는다.
        newRoutine()
    }
    yield()
    printWithThread("End")
}

// suspend : 다른 suspend fun을 호출할 수 있다.
// yield()가 suspend fun 이기 때문에 필요
suspend fun newRoutine() {
    val num1 = 1
    val num2 = 2
    yield() // 지금 코루틴을 중단하고 다른 코루틴이 실행되도록 스레드를 양보한다
    printWithThread(num1 + num2)
}

fun printWithThread(str: Any) {
    println("[${Thread.currentThread().name}] $str")
}