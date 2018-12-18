package com.runningmessage.weather.db

import android.database.sqlite.SQLiteDatabase
import com.runningmessage.weather.db.model.CityForecast
import com.runningmessage.weather.db.model.DayForecast
import com.runningmessage.weather.domain.ForecastDataSource
import com.runningmessage.weather.domain.model.Forecast
import com.runningmessage.weather.domain.model.ForecastList
import com.runningmessage.weather.utils.convertDate
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Created by Lorss on 18-12-17.
 */
class ForecastDb(
        private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance
        , private val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {

    override fun requestDayForecast(id: Long): Forecast? = forecastDbHelper.use {

        val forecast = select(DayForecastTable.NAME).byId(id).parseOpt {
            DayForecast(HashMap(it))
        }
//        if (forecast != null) dataMapper.convertDayToDomain(forecast) else null
        forecast?.let { dataMapper.convertDayToDomain(it) }
    }


    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"

        log("ForecastDb", "#select: date >= $date")
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ZIP_CODE} = ?", zipCode.toString())
                .parseOpt {
                    CityForecast(HashMap(it), dailyForecast)
                }

//        if (city != null) dataMapper.convertToDomain(city) else null
        city?.let { dataMapper.convertToDomain(it) }
    }

    fun saveForecast(forecastList: ForecastList) = forecastDbHelper.use {

        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecastList)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())

            dailyForecast.forEach {
                val id = insert(DayForecastTable.NAME, *it.map.toVarargArray())
                log("ForecastDb", "#insert: id = $id, date = ${it.date}")
            }
        }
    }


}

class DbDataMapper {
    fun convertToDomain(cityForecast: CityForecast) = with(cityForecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(zipCode, _id.toString(), city, country, daily)
    }

    fun convertDayToDomain(dayForecast: DayForecast) = with(dayForecast) {
        Forecast(_id, (date * 1000).convertDate(), description, high, low, iconUrl)
    }

    fun convertFromDomain(forecastList: ForecastList) = with(forecastList) {
        val daily = dailyForecast.map { convertDayFromDomain(zipCode, it) }
        CityForecast(zipCode, id.toLong(), city, country, daily)
    }

    private fun convertDayFromDomain(zipCode: Long, forecast: Forecast) = with(forecast) {
        DayForecast(date.convertDate(), description, high, low, iconUrl, zipCode)
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

private fun SelectQueryBuilder.byId(id: Long): SelectQueryBuilder = whereSimple("_id = ?", id.toString())

fun SQLiteDatabase.clear(tableName: String) {
    execSQL("delete from $tableName")
}

fun <K, V : Any> MutableMap<K, V?>.toVarargArray(): Array<out Pair<K, V>> = map { Pair(it.key, it.value!!) }.toTypedArray()