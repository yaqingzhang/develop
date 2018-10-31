package com.lorss.kotlin.test

/**
 * Created by Lorss on 18-9-26.
 */
class Chapter7 {


    class Point(val x: Int, val y: Int) {

        operator fun component1(): Int = x
        operator fun component2(): Int = y
    }

    data class NameComponents(val name: String, val extention: String)

    class Person {

        val emails by lazy { loadEmails() }

        private fun loadEmails(): List<String> {
            return ArrayList()
        }
    }


    companion object {

        fun t74() {
            val p = Point(1, 2)

            val (a, b) = p

            val (name, extention) = splitFileName("a.txt")
        }

        fun splitFileName(fullName: String): NameComponents {
            val result = fullName.split('.', limit = 2)

            return NameComponents(result[0], result[1])
        }


        fun t751() {

        }
    }
}