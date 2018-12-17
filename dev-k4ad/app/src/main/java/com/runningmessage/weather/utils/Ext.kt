package com.runningmessage.weather.utils

import android.content.Context
import android.view.View
import java.text.DateFormat
import java.util.*

/**
 * Created by Lorss on 18-12-10.
 */
val View.ctx: Context
    get() = context


val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())

fun String.convertDate() = df.parse(this).time

fun Long.convertDate() = df.format(this)