package com.runningmessage.kotref.kotlin.coroutines

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*

/**
 * 协程上下文与调度器
 *
 * Created by Lorss on 18-11-28.
 */
class ContextDispatchers {

    companion object {

        /**调度器与线程*/
        fun t01() = wrap {

            runBlocking {

                launch {
                    mPrintln("main bunBlocking : I'm working in thread ${Thread.currentThread().name}")
                }

                launch(Dispatchers.Unconfined) {
                    mPrintln("Unconfined : I'm working in thread ${Thread.currentThread().name}")
                }

                launch(Dispatchers.Default) {
                    mPrintln("Default : I'm working in thread ${Thread.currentThread().name}")
                }


                launch(newSingleThreadContext("MyOwnThread")) {
                    mPrintln("Default : I'm working in thread ${Thread.currentThread().name}")
                }

            }
            /**[t01]*/
        }


        /**非受限调度器vs受限调度器*/
        fun t02() = wrap {

            runBlocking {

                launch(Dispatchers.Unconfined) {

                    mPrintln("Unconfined: I'm working in thread ${Thread.currentThread().name}")

                    delay(500)
                    mPrintln("Unconfined: After delay in thread ${Thread.currentThread().name}")
                }

                launch {

                    mPrintln("main runBlocking: I'm working in thread ${Thread.currentThread().name}")

                    delay(1000)
                    mPrintln("main runBlocking: After delay in thread ${Thread.currentThread().name}")
                }
            }
            /**[t02]*/
        }

        /**调试协程与线程*/
        fun t03() = wrap {

            runBlocking {

                val a = async {
                    log("I'm computing a piece of the answer")
                    6
                }

                val b = async {
                    log("I'm computing another piece of the answer")
                    7
                }

                log("The answer is ${a.await() + b.await()}")
            }
            /**[t03]*/
        }

        private fun StringBuilder.log(msg: String) = mPrintln("[${Thread.currentThread().name}] $msg")


        /**在不同线程间跳转*/
        fun t04() = wrap {

            newSingleThreadContext("Ctx1").use { ctx1 ->

                newSingleThreadContext("Ctx2").use { ctx2 ->

                    runBlocking(ctx1) {
                        log("Started in ctx1")

                        withContext(ctx2){
                            log("Working in ctx2")
                        }

                        log("Back to ctx1")
                    }
                }

            }
            /**[t04]*/
        }


        /**上下文中的任务*/
        fun t05() = wrap {

            runBlocking {
                mPrintln("My job is ${coroutineContext[Job]}")
            }
            /**[t05]*/
        }

    }
}