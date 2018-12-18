package com.runningmessage.weather.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import java.text.DateFormat
import java.util.*

/**
 * Created by Lorss on 18-12-10.
 */
val View.ctx: Context
    get() = context

fun View.slideExit() {
    if (translationY == 0f) animate().translationY(-height.toFloat())
}

fun View.slideEnter() {
    if (translationY < 0f) animate().translationY(0f)
}

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())

fun String.convertDate() = df.parse(this).time

fun Long.convertDate() = df.format(this)