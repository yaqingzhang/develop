package com.runningmessage.weather.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.runningmessage.weather.R
import com.runningmessage.weather.domain.RequestForecastCommand
import com.runningmessage.weather.utils.DelegatesExt
import com.runningmessage.weather.utils.anko.uiThreadDelayed
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.async
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), ToolbarManager {

    var zipCode: Long by DelegatesExt.preference(this, SettingActivity.ZIP_CODE, SettingActivity.DEFAULT_ZIP)
    var requestingZipCoe: Long = -1L
    var showZipCoe: Long = -1L

    override val toolbar: Toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }

    companion object {
        private const val LOADING_TIME_MIN = 2500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)


        button.setOnClickListener {
            loadForecast()
        }
    }

    override fun onResume() {
        super.onResume()
        if (zipCode != showZipCoe) {
            loadForecast()
        }
    }

    private fun loadForecast() =
            async {

                val requestZipCode = zipCode

                if (requestingZipCoe == requestZipCode) return@async

                requestingZipCoe = requestZipCode

                val start = System.currentTimeMillis()
                uiThread {
                    val anim = RotateAnimation(0f, 360f
                            , Animation.RELATIVE_TO_SELF, .5f
                            , Animation.RELATIVE_TO_SELF, .5f).apply {
                        duration = 1000
                        interpolator = AccelerateInterpolator()
                        repeatCount = Animation.INFINITE
                    }

                    button.startAnimation(anim)
                }
                val items = RequestForecastCommand(requestZipCode).execute()

                val end = System.currentTimeMillis()
                uiThreadDelayed(LOADING_TIME_MIN - (end - start)) {
                    button.clearAnimation()
                    if (items != null) {

                        requestingZipCoe = -1L
                        showZipCoe = requestZipCode

                        toolbarTitle = "${items.city}(${items.country})"

                        forecastList.adapter = ForecastListAdapter(items) { item ->
                            startActivity<DetailActivity>(
                                    DetailActivity.ID to item.id,
                                    DetailActivity.CITY_NAME to items.city
                            )
                        }
                    }
                }
            }

}
