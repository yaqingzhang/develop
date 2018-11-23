package com.runningmessage.kotref.kotlin.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Lorss on 18-11-23.
 */
class Basic {


    companion object {


        fun t01(): Any {
            val sb = StringBuilder()

            GlobalScope.launch {

                delay(1000L)

                sb.append("World!")
            }

            sb.append("Hello, ")
            return sb.toString()
        }

        fun t02(): Any {
            val sb = StringBuilder()
            GlobalScope.launch {
                delay(1000L)
                sb.append("World!")
            }

            sb.append("Hello, ")
            runBlocking {
                delay(2000L)
            }
            return sb.toString()
        }

        fun t03(): Any {
            val sb = StringBuilder()

            runBlocking {
                val job = GlobalScope.launch {
                    delay(1000L)
                    sb.append("World!")
                }

                sb.append("Hello, ")
                job.join()
            }

            return sb
        }

        fun t04(): Any {
            val sb = StringBuilder()

            runBlocking {
                launch {
                    delay(1000L)
                    sb.append("World!")
                }

                sb.append("Hello, ")
            }

            return sb
        }


    }


}