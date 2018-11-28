package com.runningmessage.kotref.kotlin.coroutines

import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*

/**
 * 基础
 *
 * Created by Lorss on 18-11-23.
 */
class Basic {


    companion object {


        /**你的第一个协程程序*/
        fun t01() = wrap {

            GlobalScope.launch {

                delay(1000L)

                mPrintln("World!")
            }

            mPrintln("Hello, ")
        }


        /**桥接阻塞与非阻塞的世界*/
        fun t02() = wrap {

            GlobalScope.launch {
                delay(1000L)
                mPrintln("World!")
            }

            mPrintln("Hello, ")
            runBlocking {
                delay(2000L)
            }

        }

        /**等待一个任务*/
        fun t03() = wrap {


            runBlocking {
                val job = GlobalScope.launch {
                    delay(1000L)
                    mPrintln("World!")
                }

                mPrintln("Hello, ")
                job.join()
            }


        }

        /**结构化的并发*/
        fun t04() = wrap {


            runBlocking {
                launch {
                    delay(1000L)
                    mPrintln("World!")
                }

                mPrintln("Hello, ")
            }


        }

        /**作用域构建器*/
        //TODO m:lorss analyse
        fun t05() = wrap {


            runBlocking {

                launch {
                    delay(200L)
                    mPrintln("\nTask from runBlocking")
                }

                coroutineScope {
                    //Not blocking when waiting for complete

                    launch {
                        delay(500L)
                        mPrintln("\nTask from nested launch")
                    }

                    delay(100L)
                    mPrintln("\nTask from coroutineScope")
                }

                mPrintln("\nCoroutineScope is over")
            }


        }


        /**提取函数重构*/
        fun t06() = wrap {

            runBlocking {
                launch {
                    delay(1000L)
                    mPrintln("Hello, ")
                }

                coroutineScope {
                    launch {
                        delay(2000L)
                        mPrintln("CoroutineScope: ")
                    }
                }

                mPrintln("Wrold! ")
            }


        }


    }


}