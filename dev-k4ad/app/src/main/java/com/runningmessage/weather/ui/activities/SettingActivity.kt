package com.runningmessage.weather.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.runningmessage.weather.R
import com.runningmessage.weather.utils.DelegatesExt
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Lorss on 18-12-18.
 */
class SettingActivity : AppCompatActivity() {


    companion object {
        const val ZIP_CODE = "zipCode"
        const val DEFAULT_ZIP = 94043L
    }

    var zipCode: Long by DelegatesExt.preference(this, ZIP_CODE, DEFAULT_ZIP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)

        cityCode.setText(zipCode.toString())

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

    override fun onBackPressed() {
        super.onBackPressed()
        try {
            zipCode = cityCode.text.toString().toLong()
        } catch (ignored: Exception) {
        }
    }
}