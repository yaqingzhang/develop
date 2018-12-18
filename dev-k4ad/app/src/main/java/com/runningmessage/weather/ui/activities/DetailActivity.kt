package com.runningmessage.weather.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.runningmessage.weather.R
import com.runningmessage.weather.domain.RequestDayForecastCommand
import com.runningmessage.weather.domain.model.Forecast
import com.runningmessage.weather.utils.color
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

/**
 * Created by Lorss on 18-12-18.
 */
class DetailActivity : AppCompatActivity() {

    companion object {

        val ID = "DetailActivity:id"
        val CITY_NAME = "DetailActivity:cityName"
    }

    var title: String = ""
    var id: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)
        title = intent.getStringExtra(CITY_NAME)
        id = intent.getLongExtra(ID, id)
        async {

            val result = RequestDayForecastCommand(id).execute()
            uiThread {
                bindForecast(result)
            }
        }
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {

        Picasso.get().load(iconUrl).into(icon)

        supportActionBar?.subtitle = date
        weatherDescription.text = description

        bindWeather(high to maxTemperature, low to minTemperature)
    }

    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first.toString()}"
        it.second.setTextColor(color(when (it.first) {
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        }))

    }


}