package com.runningmessage.weather.domain.model

/**
 * Created by Lorss on 18-12-5.
 */
data class ForecastList(val id: String, val city: String, val country: String, val dailyForecast: List<Forecast>) {

    fun size(): Int = dailyForecast.size

    operator fun get(i: Int): Forecast = dailyForecast[i]
}

data class Forecast(val date: String, val description: String, val high: Int, val low: Int, val iconUrl: String)