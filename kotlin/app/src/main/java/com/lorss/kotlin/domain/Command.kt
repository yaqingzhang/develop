package com.lorss.kotlin.domain

/**
 * Created by Lorss on 18-9-13.
 */
public interface Command<T> {

    fun excute(): T
}