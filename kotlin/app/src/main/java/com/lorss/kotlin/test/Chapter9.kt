package com.lorss.kotlin.test

@Suppress("unused")
/**
 * Created by Lorss on 18-9-28.
 */
class Chapter9 {


    companion object {

        //实化类型参数

        //声明点变型


        private fun <T> List<T>.slice(indices: IntRange): List<T> {
            val slice: MutableList<T> = mutableListOf()

            withIndex().forEach { (index: Int, item: T) ->
                if (index in indices) slice.add(item)
            }

            return slice
        }

        fun t11(): Any = ('a'..'z').toList().slice(10..13)

        private fun pNull(data: String?) = data ?: 0
        private fun pNull(data: Int?) = data ?: -1

        fun t111() = pNull("aaa")


        fun t21(): Any {

            return try {
                calcSum(('a'..'z').toList())
            } catch (e: Exception) {
                return e.toString()// is ClassCastException rather than IllegalArgumentException
            }
        }

        @Suppress("UNCHECKED_CAST")
        private fun calcSum(c: Collection<*>): Int {
            val intList = c
                    as? List<Int>
                    ?: throw IllegalArgumentException("c is not intList")

            return intList.sum()
        }

        fun t22(): Any = listOf("one", 1, "two").filterIsInstance<String>()


        private inline fun <reified T> isA(value: Any) = value is T


        fun t32() = null

        fun test() {
            val list: Collection<*> = ('z'..'Z').toList()
            val b = list is List
            println(isA<Boolean>(b))
        }
    }


}