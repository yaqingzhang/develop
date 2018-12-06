package com.runningmessage.weather.domain.model

/**
 * Created by Lorss on 18-12-5.
 */
data class ForecastList(val city: String, val country: String, val dailyForecast: List<Forecast>)

data class Forecast(val date: String, val description: String, val high: Int, val low: Int)