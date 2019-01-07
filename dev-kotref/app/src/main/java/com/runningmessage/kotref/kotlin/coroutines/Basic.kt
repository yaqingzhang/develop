package com.runningmessage.kotref.kotlin.coroutines

import com.runningmessage.kotref.utils.log
import com.runningmessage.kotref.utils.mPrintln
import com.runningmessage.kotref.utils.sLog
import com.runningmessage.kotref.utils.wrap
import kotlinx.coroutines.*
import java.util.concurrent.locks.LockSupport
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

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
                // 在后台启动一个新的协程并继续 TODO

                delay(1000L)// 非阻塞的等待 1 秒

                mPrintln("World!")
                sLog("#GlobalScope.launch")
            }

            mPrintln("Hello, ")
            log("#Main")
            Thread.sleep(2000)// 阻塞主线程 2 秒来保证 JVM 存活
        }


        /**桥接阻塞与非阻塞的世界*/
        fun t02() = wrap {

            GlobalScope.launch {
                delay(1000L)
                mPrintln("World!")
                sLog("#GlobalScope.launch")
            }

            mPrintln("Hello, ")
            runBlocking {
                // 这个表达式阻塞主线程
                /**
                 * see [BlockingEventLoop.processNextEvent],
                 * [BlockingEventLoop.schedule],
                 * [BlockingEventLoop.shouldUnpark]
                 *
                 * see [BlockingCoroutine.joinBlocking],
                 * [TimeSource.parkNanos],
                 * [LockSupport.parkNanos]
                 *
                 * see [BlockingCoroutine.onCompletionInternal],
                 * [LockSupport.unpark]
                 * */
                delay(2000L)
                sLog("#Main.runBlocking")
            }

        }

        /**等待一个任务*/
        fun t03() = wrap {


            runBlocking {
                val job = GlobalScope.launch {
                    // 启动一个新协程并保持对这个作业的引用
                    delay(1000L)
                    mPrintln("World!")
                }

                mPrintln("Hello, ")
                /**
                 * see [JobSupport.join],
                 * [JobSupport.joinSuspend],
                 * [suspendCancellableCoroutine],
                 * [suspendCoroutineUninterceptedOrReturn]
                 * */
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