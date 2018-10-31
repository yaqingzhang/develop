package com.lorss.rxjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arr = ArrayList<String>()
        arr.add("1.png")
        arr.add("2.jpg")
        arr.add("3.webp")
        arr.add("4.png")

        //loopArr(arr)
        loopArrRx(arr)
    }


    private fun loopArr(arr: List<String>) {
        Thread {
            for (item in arr) {
                if (item.endsWith(".png")) {
                    this@MainActivity.runOnUiThread {
                        val text = TextView(this@MainActivity)
                        text.text = item
                        root_view.addView(text)
                    }
                }
            }
        }.start()
    }

    private fun loopArrRx(arr: List<String>) {
        Observable.fromIterable(arr)
                .filter { it.endsWith(".png") }
                .map {
                    val text = TextView(this@MainActivity)
                    text.text = it
                    text
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { root_view.addView(it) }
    }
}
