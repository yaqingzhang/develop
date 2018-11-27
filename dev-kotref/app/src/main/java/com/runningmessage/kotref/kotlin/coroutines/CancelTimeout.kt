package com.runningmessage.kotref.kotlin.coroutines

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*

/**
 * Created by Lorss on 18-11-26.
 */
class CancelTimeout {


    companion object {


        /**取消协程的执行*/
        fun t01() = wrap {
            runBlocking {

                val job = launch {

                    repeat(2000) {
                        append("\nI'm Sleeping $it")
                        delay(500)
                    }
                }

                delay(1500L)
                mPrintln("\nmain: I'm tired of waiting!")
                job.cancel()
                job.join()
                mPrintln("main: Now I can quit!")

            }
        }

        /**取消是协作的*/
        fun t02() = wrap {
            runBlocking {
                val startTime = System.currentTimeMillis()
                val job = launch(Dispatchers.Default) {

                    var nextPrintTime = startTime

                    var i = 0

                    while (i < 5) {
                        if (System.currentTimeMillis() >= nextPrintTime) {
                            mPrintln("I'm sleeping ${i++} ...")
                            nextPrintTime += 500L
                        }
                    }

                }

                delay(1300L)
                mPrintln("main: I'm tired of waiting!")
                job.cancelAndJoin()
                mPrintln("main: Now I can quit.")
            }
        }


        /**在 finally 中释放资源*/
        fun t03() = wrap {
            runBlocking {

                val job = launch {

                    try {
                        repeat(2000) {
                            append("\nI'm Sleeping $it")
                            delay(500)
                        }
                    } finally {
                        mPrintln("I'm running finally...")
                    }
                }

                delay(1500L)
                mPrintln("\nmain: I'm tired of waiting!")
                job.cancelAndJoin()
                mPrintln("main: Now I can quit!")

            }
        }


        /**运行不能取消的代码块*/
        fun t04() = wrap {
            runBlocking {

                val job = launch {
                    try {
                        repeat(1000) {
                            mPrintln("I'm sleeping $it ...")
                            delay(500L)
                        }
                    } finally {
                        withContext(NonCancellable) {
                            mPrintln("I'm running finally")
                            delay(1000L)
                            mPrintln("And I've just delayed for 1 sec because I'm non-cacellable")
                        }
                    }
                }

                delay(1500L)
                mPrintln("\nmain: I'm tired of waiting!")
                job.cancelAndJoin()
                mPrintln("main: Now I can quit!")
            }
        }

        /**超时*/
        fun t05() = wrap {


            runBlocking {

                //withTimeoutOrNull not throw Exception but return null
                withTimeout(1300L) {
                    repeat(1000) {
                        mPrintln("I'm sleeping $it ...")
                        delay(500)
                    }
                }
            }
        }
    }


}