package com.runningmessage.weather.domain

import com.runningmessage.weather.domain.model.Forecast
import com.runningmessage.weather.domain.model.ForecastList

/**
 * Created by Lorss on 18-12-5.
 */
class RequestDayForecastCommand(private val id: Long, private val forecastProvider: ForecastProvider = ForecastProvider())
    : Command<Forecast> {

    override fun execute(): Forecast =
            forecastProvider.requestForecast(id)
}

class RequestForecastCommand(private val zipCode: Long
                             , private val forecastProvider: ForecastProvider = ForecastProvider()) : Command<ForecastList?> {

    override fun execute(): ForecastList? {
        try {
            return forecastProvider.requestByZipCode(zipCode, DAYS)
        } catch (e: Exception) {
            log("RequestForecastCommand", "#execute : ", e)
        }
        return null
    }

    companion object {

        private const val DAYS = 7
    }
}