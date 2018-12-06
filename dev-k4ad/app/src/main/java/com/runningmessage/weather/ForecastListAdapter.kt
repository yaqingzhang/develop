package com.runningmessage.weather

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.runningmessage.weather.domain.model.ForecastList

/**
 * Created by Lorss on 18-12-5.
 */
class ForecastListAdapter(private val items: ForecastList) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.context))
    }


    override fun getItemCount(): Int {
        return items.dailyForecast.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items.dailyForecast[position]) {
            holder.textView.text = "$date - $description - $high/$low"
        }
    }


    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}