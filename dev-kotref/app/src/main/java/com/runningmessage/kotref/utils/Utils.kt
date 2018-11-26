package com.runningmessage.kotref.utils

/**
 * Created by Lorss on 18-11-26.
 */
fun wrap(apply: StringBuilder.() -> Unit): Any {
    val sb = StringBuilder()
    try {
        apply.invoke(sb)
    } catch (e: Exception) {
        sb.mPrintln(e.toString())
    } finally {
    }
    return sb
}

fun StringBuilder.mPrintln(obj: Any?) {
    append(obj?.toString() ?: "").append("\n")
}