package com.runningmessage.weather.domain

/**
 * Created by Lorss on 18-12-5.
 */
interface Command<T> {

    fun execute(): T
}