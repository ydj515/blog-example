package basic.suspend

import basic.printWithThread
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    launch {
        a()
        b()
    }

    launch {
        c()
    }
}

suspend fun a() {
    printWithThread("a")
}

suspend fun b() {
    printWithThread("b")
}

suspend fun c() {
    printWithThread("c")
}