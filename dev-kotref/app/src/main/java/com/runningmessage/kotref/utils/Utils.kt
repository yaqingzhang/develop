package com.runningmessage.kotref.utils

/**
 * Created by Lorss on 18-11-26.
 */
fun wrap(apply: StringBuilder.() -> Unit): Any {
    val sb = StringBuilder()
    apply.invoke(sb)
    return sb
}

fun StringBuilder.println(obj: Any?) {
    append(obj?.toString() ?: "").append("\n")
}