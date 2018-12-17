package com.runningmessage.weather.domain

import com.runningmessage.weather.db.ForecastDb
import com.runningmessage.weather.domain.model.ForecastList
import com.runningmessage.weather.net.ForecastRequest

/**
 * Created by Lorss on 18-12-17.
 */
class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper()
                     , private val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {


    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {

        val result = ForecastRequest(zipCode.toString()).execute()

        val converted = dataMapper.convertFromDataModel(result)


        forecastDb.saveForecast(converted)

        return forecastDb.requestForecastByZipCode(zipCode, date)

    }

}