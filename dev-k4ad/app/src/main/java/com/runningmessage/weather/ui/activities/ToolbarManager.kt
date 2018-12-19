package com.runningmessage.weather.ui.activities

import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.RecyclerView
import com.runningmessage.weather.App
import com.runningmessage.weather.R
import com.runningmessage.weather.utils.ctx
import com.runningmessage.weather.utils.slideEnter
import com.runningmessage.weather.utils.slideExit
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 * Created by Lorss on 18-12-18.
 */
interface ToolbarManager {

    val toolbar: android.support.v7.widget.Toolbar

    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    var toolbarSubtitle: String
        get() = toolbar.subtitle.toString()
        set(value) {
            toolbar.subtitle = value
        }

    fun initToolbar() {
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.menu_setting -> {
                    toolbar.ctx.startActivity<SettingActivity>()
                }

                else -> App.instance.toast("Unknown option")
            }

            true
        }
    }

    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }

//    fun createUpDrawable() = with(DrawerArrowDrawable(toolbar.ctx)) {
//        progress = 1f
//        this
//    }

    fun createUpDrawable() = DrawerArrowDrawable(toolbar.ctx).apply {
        progress = 1f
    }

    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) toolbar.slideExit() else toolbar.slideEnter()
            }
        })
    }
}