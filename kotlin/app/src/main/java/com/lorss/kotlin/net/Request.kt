package com.lorss.kotlin.net

import com.google.gson.Gson
import com.lorss.kotlin.domain.Command
import com.lorss.kotlin.domain.ForecastList
import com.lorss.kotlin.model.ForecastDataMapper
import com.lorss.kotlin.model.ForecastResult
import java.net.URL

/**
 * Created by Lorss on 18-9-11.
 */
class Request(private var zipCode: String) : Command<ForecastResult> {


    override fun excute(): ForecastResult {
        val forceCastJsonStr = URL(COMPLETE_URL + zipCode).readText()
        return Gson().fromJson(forceCastJsonStr, ForecastResult::class.java)
    }

    companion object {
        private const val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"

        private const val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7"

        private const val COMPLETE_URL = "$URL&APPID=$APP_ID&q="
    }
}


class Req(val zipCode: String) : Command<ForecastList> {
    override fun excute(): ForecastList {
        return ForecastDataMapper().convertFromDataModel(Request(zipCode).excute())
    }

}