package com.runningmessage.weather

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.runningmessage.weather.domain.model.Forecast
import com.runningmessage.weather.domain.model.ForecastList
import com.runningmessage.weather.utils.ctx
import com.squareup.picasso.Picasso

/**
 * Created by Lorss on 18-12-5.
 */
class ForecastListAdapter(private val items: ForecastList, val itemClick: (Forecast) -> Unit) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }


    override fun getItemCount(): Int {
        return items.size()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(items[position])
    }


    class ViewHolder(val view: View, val itemClick: (Forecast) -> Unit) : RecyclerView.ViewHolder(view) {

        private val iconView: ImageView = view.findViewById(R.id.icon)
        private val dateView: TextView = this.view.findViewById(R.id.date)
        private val descriptionView: TextView = view.findViewById(R.id.description)
        private val maxTemperatureView: TextView = view.findViewById(R.id.maxTemperature)
        private val minTemperatureView: TextView = view.findViewById(R.id.minTemperature)

        fun bindForecast(forecast: Forecast) {
            with(forecast) {

                Picasso.get().load(iconUrl).into(iconView)

                dateView.text = date
                descriptionView.text = description
                maxTemperatureView.text = high.toString()
                minTemperatureView.text = low.toString()
                itemView.setOnClickListener { itemClick(forecast) }
            }

        }
    }
}