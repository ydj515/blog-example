package basic.suspend

import basic.printWithThread
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

//[main] start
//[main] 30
//[main] end
fun main(): Unit = runBlocking {

    printWithThread("start")
    printWithThread(caculateResult())
    printWithThread("end")
}

//coroutineScope 대신 withContext(Dispatchers.Default)로도 사용 가능
suspend fun caculateResult(): Int = coroutineScope {
    val num1 = async {
        delay(1000)
        10
    }

    val num2 = async {
        delay(1000)
        20
    }

    num1.await() + num2.await()
}
