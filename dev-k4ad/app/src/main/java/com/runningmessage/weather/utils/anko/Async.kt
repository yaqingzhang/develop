package com.runningmessage.weather.utils.anko

import android.os.Handler
import android.os.Looper
import org.jetbrains.anko.AnkoAsyncContext
import org.jetbrains.anko.uiThread

/**
 * Created by Lorss on 18-12-5.
 */
fun <T> AnkoAsyncContext<T>.uiThreadDelayed(time: Long, f: (T) -> Unit) {
    val ref = weakRef.get() ?: return
    if (time <= 0) uiThread(f)
    else
        ContextHelper.handler.postDelayed({ f(ref) }, time)

}

private object ContextHelper {
    val handler = Handler(Looper.getMainLooper())
    val mainThread = Looper.getMainLooper().thread
}