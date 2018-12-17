package com.runningmessage.weather.db

import com.runningmessage.weather.utils.ALog

/**
 * Created by Lorss on 18-12-5.
 */
const val PACKAGE = "DB"

fun log(tag: String, msg: String, vararg e: Throwable) {
    ALog.m(PACKAGE).v(tag, msg, *e)
}