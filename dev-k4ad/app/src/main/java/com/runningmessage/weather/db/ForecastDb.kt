package com.runningmessage.weather.db

import android.database.sqlite.SQLiteDatabase
import com.runningmessage.weather.db.model.CityForecast
import com.runningmessage.weather.db.model.DayForecast
import com.runningmessage.weather.domain.model.Forecast
import com.runningmessage.weather.domain.model.ForecastList
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Created by Lorss on 18-12-17.
 */
class ForecastDb(
        val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance
        , val dataMapper: DbDataMapper = DbDataMapper()) {


    fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"

        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt {
                    CityForecast(HashMap(it), dailyForecast)
                }

        if (city != null) dataMapper.convertToDomain(city) else null
    }

    fun saveForecast(forecastList: ForecastList) = forecastDbHelper.use {

        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecastList)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())

            dailyForecast.forEach {
                insert(DayForecastTable.NAME, *it.map.toVarargArray())
            }
        }
    }


}

class DbDataMapper {
    fun convertToDomain(cityForecast: CityForecast) = with(cityForecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id.toString(), city, country, daily)
    }

    private fun convertDayToDomain(dayForecast: DayForecast) = with(dayForecast) {
        Forecast(date.toString(), description, high, low, iconUrl)
    }

    fun convertFromDomain(forecastList: ForecastList) = with(forecastList) {
        val daily = dailyForecast.map { convertDayFromDomain(id.toLong(), it) }
        CityForecast(id.toLong(), city, country, daily)
    }

    private fun convertDayFromDomain(id: Long, forecast: Forecast) = with(forecast) {
        DayForecast(date.toLong(), description, high, low, iconUrl, id)
    }

}

private fun <T : Any> SelectQueryBuilder.parseList(parser: (Map<String, Any>) -> T) =
        parseList(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any>): T = parser(columns)
        })

private fun <T : Any> SelectQueryBuilder.parseOpt(parser: (Map<String, Any>) -> T) =
        parseOpt(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any>) = parser(columns)
        })

fun SQLiteDatabase.clear(tableName: String) {
    execSQL("delete from $tableName")
}

fun <K, V : Any> MutableMap<K, V?>.toVarargArray(): Array<out Pair<K, V>> = map { Pair(it.key, it.value!!) }.toTypedArray()