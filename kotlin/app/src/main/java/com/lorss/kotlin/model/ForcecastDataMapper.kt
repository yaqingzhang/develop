package com.lorss.kotlin.model

import com.lorss.kotlin.domain.ForecastList
import java.text.DateFormat
import java.util.*

typealias  ModelForecast = com.lorss.kotlin.domain.Forecast

/**
 * Created by Lorss on 18-9-13.
 */
public class ForecastDataMapper {
    fun convertFromDataModel(forecast: ForecastResult): ForecastList {

        fun convertDate(date: Long): String {
            val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
            return df.format(date * 1000)
        }

        fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
            return ModelForecast(convertDate(forecast.dt),
                    forecast.weather[0].description, forecast.temp.max.toInt(), forecast.temp.min.toInt())
        }

        fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
            return list.map { convertForecastItemToDomain(it) }
        }

        return ForecastList(forecast.city.name, forecast.city.country, convertForecastListToDomain(forecast.list))

    }
}