package com.lorss.kotlin.test

import java.io.FileInputStream

/**
 * Created by Lorss on 18-9-27.
 */
class Chapter8 {

    companion object {

        private val log = listOf(
                SiteVisit("/", 34.0, Os.WIN),
                SiteVisit("/", 22.0, Os.MAC),
                SiteVisit("/login", 12.0, Os.WIN),
                SiteVisit("/signup ", 8.0, Os.IOS),
                SiteVisit("/", 16.3, Os.ANDROID))

        fun t12(): Any {
            return call { a, b -> a + b }
        }

        private fun call(f: (Int, Int) -> Int): Any = f(2, 3)

        fun t16(): Any = averageDurationFor { it.os == Os.WIN }


        private fun averageDurationFor(predicate: (SiteVisit) -> Boolean) = log.asSequence().filter(predicate).map { it.duration }.average()


        fun t25(): String {

            var r: Any

            try {
                r = FileInputStream("/kt.log").use { readLine() } ?: "$ empty $"
            } catch (e: Exception) {
                return "$ Exception $e"
            }

            return r.toString()
        }

        fun t32(): Int {

            var index = 0
            log.forEach {
                index++
                if (it.os == Os.IOS)
                    return@forEach
            }


            return index
        }


        fun t32f1(): String {
            return StringBuilder().apply sb@{
                listOf(1, 2, 3).apply { this@sb.append(this.toString()) }
            }.toString()
        }

        fun t33() {}


    }

    enum class Os {
        WIN, LINUX, MAC, IOS, ANDROID
    }

    data class SiteVisit(val path: String, val duration: Double, val os: Os)


}