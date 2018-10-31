package com.lorss.kotlin.test

import com.lorss.kotlin.test.java.DataParser
import com.lorss.kotlin.test.java.FileContentProcessor
import com.lorss.kotlin.test.java.StringProcessor
import java.io.File

/**
 * Created by Lorss on 18-9-20.
 */
class Chapter6 {

    class StringPrinter : StringProcessor {

        override fun process(value: String?) {
            println(value)
        }

    }

    class NullableStringPrinter : StringProcessor {
        override fun process(value: String?) {
            if (value != null) {
                println(value)
            }
        }
    }

    abstract class Test<T> : Any() {

        open abstract fun process(): T
    }

    class SubTest : Test<Unit>() {

        override fun process() {
        }

    }

    class MyProcessor : FileContentProcessor, DataParser<Chapter5.Person> {

        override fun parseData(input: String
                               , output: MutableList<Chapter5.Person>
                               , errors: MutableList<String?>) {
        }

        override fun processContents(path: File
                                     , binaryContents: ByteArray?
                                     , textContents: List<String>?) {
        }

    }


    companion object {

        fun t623(): Int {
            try {
                return "".toInt()
            } catch (e: Exception) {
                return 0
            }
        }


        fun t626(): Int {
            val p = Chapter5.Person("", 1)
            val name = p.name ?: fail()
            return name.length
        }

        fun fail(): Nothing {
            throw Exception("fail")
        }

    }


}