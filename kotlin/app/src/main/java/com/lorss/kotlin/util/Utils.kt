package com.lorss.kotlin.util

import java.util.*

/**
 * Created by Lorss on 18-8-17.
 */
class Utils private constructor() {

    val defaultInt = 0
    private val privateInt = -1
    protected val protectedInt = 1
    public val publicInt = 2

    open fun randomInt(): Int {
        return Random().nextInt()
    }

    private object Holder {
        val INSTANCE = Utils()
    }

    companion object {

        fun instance(): Utils {
            return Holder.INSTANCE
        }

        fun toString(any: Any): String {
            if (any is IntArray) {
                val str = StringBuilder()
                str.append("[ ")
                for ((index, value) in any.withIndex()) {
                    if (index == any.size - 1) {
                        str.append("$value ")
                    } else {
                        str.append("$value, ")
                    }
                }
                str.append("]")
                return str.toString()
            }

            return ""
        }


    }


}