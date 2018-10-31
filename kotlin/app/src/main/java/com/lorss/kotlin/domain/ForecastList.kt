package com.lorss.kotlin.domain

/**
 * Created by Lorss on 18-9-13.
 */
data class ForecastList(val city: String, val country: String, val dailyForecast: List<Forecast>)

data class Forecast(val date: String, val description: String, val high: Int, val low: Int)