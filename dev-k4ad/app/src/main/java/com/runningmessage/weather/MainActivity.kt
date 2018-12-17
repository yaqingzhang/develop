package com.runningmessage.weather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.runningmessage.weather.domain.RequestForecastCommand
import com.runningmessage.weather.utils.anko.uiThreadDelayed
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOADING_TIME_MIN = 2500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        forecast_list.layoutManager = LinearLayoutManager(this)

        button.setOnClickListener {
            async {
                val start = System.currentTimeMillis()
                uiThread {
                    val anim = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f)
                    anim.duration = 1000
                    anim.interpolator = AccelerateInterpolator()
                    anim.repeatCount = Animation.INFINITE
                    button.startAnimation(anim)
                }
                val items = RequestForecastCommand(94043).execute()

                val end = System.currentTimeMillis()
                uiThreadDelayed(LOADING_TIME_MIN - (end - start)) {
                    button.clearAnimation()
                    if (items != null) {
                        forecast_list.adapter = ForecastListAdapter(items) { item ->
                            toast(item.date)
                        }
                    }
                }
            }
        }
    }
}
