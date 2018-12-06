package com.runningmessage.weather.domain

import com.runningmessage.weather.domain.model.ForecastList
import com.runningmessage.weather.net.ForecastRequest

/**
 * Created by Lorss on 18-12-5.
 */
class RequestForecastCommand(private val zipCode: String) : Command<ForecastList> {

    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}