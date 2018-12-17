package com.runningmessage.weather.db

import android.database.sqlite.SQLiteDatabase
import com.runningmessage.weather.App
import org.jetbrains.anko.db.*

/**
 * Created by Lorss on 18-12-17.
 */
class ForecastDbHelper() : ManagedSQLiteOpenHelper(App.instance, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {

        db?.createTable(CityForecastTable.NAME, true,
                CityForecastTable.ID to INTEGER + PRIMARY_KEY,
                CityForecastTable.CITY to TEXT,
                CityForecastTable.COUNTRY to TEXT)

        db?.createTable(DayForecastTable.NAME, true,
                DayForecastTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                DayForecastTable.DATE to INTEGER,
                DayForecastTable.DESCRIPTION to TEXT,
                DayForecastTable.HIGH to INTEGER,
                DayForecastTable.LOW to INTEGER,
                DayForecastTable.ICON_URL to TEXT,
                DayForecastTable.CITY_ID to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(CityForecastTable.NAME, true)
        db?.dropTable(DayForecastTable.NAME, true)
        onCreate(db)
    }

    companion object {

        val DB_NAME = "forecast.db"
        val DB_VERSION = 1
        val instance: ForecastDbHelper    by lazy {
            ForecastDbHelper()
        }
    }
}