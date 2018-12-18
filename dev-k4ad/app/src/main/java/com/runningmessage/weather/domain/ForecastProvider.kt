package com.runningmessage.weather.domain

import com.runningmessage.weather.db.ForecastDb
import com.runningmessage.weather.domain.model.ForecastList

/**
 * Created by Lorss on 18-12-17.
 */
class ForecastProvider(private val sources: List<ForecastDataSource> = SOURCES) {


    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        requestForecast(it, days, zipCode)
    }

    fun requestForecast(id: Long) = requestToSources {
        it.requestDayForecast(id)
    }

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T = sources.firstResult { f(it) }

    private fun requestForecast(source: ForecastDataSource, days: Int, zipCode: Long): ForecastList? {

        val res = source.requestForecastByZipCode(zipCode, minTimeSpan() / 1000)

        return if (res != null && res.size() >= days) res else null
    }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    private fun yesterdayTimeSpan() = (System.currentTimeMillis() / DAY_IN_MILLIS - 1) * DAY_IN_MILLIS

    private fun minTimeSpan() = 0L

    companion object {

        const val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES = listOf(ForecastDb(), ForecastServer())
    }

    private inline fun <T, R : Any> Iterable<T>.firstResult(predicate: (T) -> R?): R {

        for (it in this) {
            val result = predicate(it)
            if (result != null) return result
        }
        throw NoSuchElementException("No element matching predicate was found.")
    }
}