package com.runningmessage.weather.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.runningmessage.weather.R
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Lorss on 18-12-18.
 */
class SettingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = item?.let {
        when (it.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> false
        }
    } ?: false
}