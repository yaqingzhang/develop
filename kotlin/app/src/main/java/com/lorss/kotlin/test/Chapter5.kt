package com.lorss.kotlin.test

/**
 * Created by Lorss on 18-9-19.
 */
object Chapter5 {

    public data class Person(val name: String?, val age: Int)
    data class Book(val title: String, val authors: List<String>)

    fun test512(): Int {
        val list = listOf(Person("5", 5), Person("6", 6))

        val p = list.maxBy(Person::age)

        val l = p?.name?.length

        list.maxBy(Person::age)

        return p?.age ?: -1
    }

    fun test524(): Int {

        val list = listOf(Book("a", listOf("aa", "ab", "c")), Book("b", listOf("ba", "bb", "c")))
        return list.flatMap { it.authors }.toSet().size
    }

    fun test552(): String {
        return with(StringBuilder()) {
            for (letter in 'A'..'Z') {
                append(letter)
            }
            toString()
        }
    }


}