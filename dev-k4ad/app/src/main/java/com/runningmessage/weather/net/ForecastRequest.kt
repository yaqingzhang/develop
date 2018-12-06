package com.runningmessage.weather.net

import com.google.gson.Gson
import com.runningmessage.weather.data.ForecastResult
import java.net.URL

/**
 * Created by Lorss on 18-12-5.
 */
class ForecastRequest(private val zipCode: String) {

    companion object {
        private const val TAG = "ForecastRequest"

        private const val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private const val URL = "http://api.openweathermap.org/data/2.5/" +
                "forecast/daily?mode=json&units=metric&cnt=7"
        private const val COMPLETE_URL = "$URL&APPID=$APP_ID&q="
    }

    fun execute(): ForecastResult {
        val forecastJsonStr = URL(COMPLETE_URL + zipCode).readText()
        log(TAG, forecastJsonStr)
        return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
    }
}