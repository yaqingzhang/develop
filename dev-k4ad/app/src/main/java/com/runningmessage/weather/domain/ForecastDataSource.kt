package com.runningmessage.weather.domain

import com.runningmessage.weather.domain.model.Forecast
import com.runningmessage.weather.domain.model.ForecastList

/**
 * Created by Lorss on 18-12-17.
 */
interface ForecastDataSource {

    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
    fun requestDayForecast(id: Long): Forecast?
}