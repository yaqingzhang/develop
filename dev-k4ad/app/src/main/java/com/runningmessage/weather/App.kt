package com.runningmessage.weather

import android.app.Application
import com.runningmessage.weather.utils.DelegatesExt

/**
 * Created by Lorss on 18-12-17.
 */
class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue<App>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}