package com.runningmessage.weather.domain

import com.runningmessage.weather.data.Forecast
import com.runningmessage.weather.data.ForecastResult
import com.runningmessage.weather.domain.model.ForecastList
import com.runningmessage.weather.utils.convertDate
import com.runningmessage.weather.domain.model.Forecast as ModelForecast

/**
 * Created by Lorss on 18-12-5.
 */
class ServerDataMapper {

    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.zipCode, forecast.city.id.toString(), forecast.city.name, forecast.city.country,
                convertForecastListToDomain(forecast.list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.map { convertForecastItemToDomain(it) }
    }

    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
        return ModelForecast(forecast.dt.convertDate(),
                forecast.weather[0].description, forecast.temp.max.toInt(),
                forecast.temp.min.toInt(), generateIconUrl(forecast.weather[0].icon))
    }

    private fun generateIconUrl(iconCode: String): String = "http://openweathermap.org/img/w/$iconCode.png"
}