package com.lorss.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.lorss.kotlin.net.Request
import com.lorss.kotlin.test.Chapter8
import com.lorss.kotlin.test.Chapter9
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.uiThread
import com.lorss.kotlin.util.Utils as U

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val ex = U.extend<String>()
//        sample_text.text =
//                """${U.toString(intArrayOf(1, 2, 3, 4))}  :  $ex"""


        var u = User(mapOf(
                "name" to "Android",
                "age" to 26
        ))


        u.name = Chapter8.t32f1()
        u.age = (Chapter8.t16() as? Double)?.toInt() ?: 0
        sample_text.text = u.info()

        val l = View.OnClickListener {
            var req = Request("94043")
            req.runInThread()
        }
        sample_text.setOnClickListener(l)

        val test = listOf(
                "Chapter8.t12( )" to { Chapter8.t12() }
                , "Chapter8.t16( )" to { Chapter8.t16() }
                , "Chapter8.t25( )" to { Chapter8.t25() }
                , "Chapter8.t32( )" to { Chapter8.t32() }
                , "Chapter8.t32f1( )" to { Chapter8.t32f1() }
                , "Chapter9.t11( )" to { Chapter9.t11() }
                , "Chapter9.t111( )" to { Chapter9.t111() }
                , "Chapter9.t21( )" to { Chapter9.t21() }
                , "Chapter9.t22( )" to { Chapter9.t22() }
        )

        if (sample_text.parent is ViewGroup) {

            for ((name, funVar) in test) {
                (sample_text.parent as ViewGroup).addButton(name, funVar)
            }
        }

    }

    private fun ViewGroup.addButton(text: String, click: () -> Any) {
        val btn = TextView(context)
        btn.gravity = Gravity.CENTER
        btn.backgroundResource = R.drawable.kt_button_bg
        btn.text = text
        btn.setMinHeight(90)
        if (this is LinearLayout) {
            val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.gravity = Gravity.CENTER_HORIZONTAL
            lp.bottomMargin = 20
            btn.layoutParams = lp
        }
        btn.setOnClickListener {
            val result = click.invoke().toString()
            val resultText = "$text = $result"
            runOnUiThread { btn.text = resultText }
        }
        addView(btn)
    }


    fun <T> U.Companion.extend(): T? {
        try {
            return "extend" as T
        } catch (e: Exception) {
            return null
        }
    }


    class User(val map: Map<String, Any?>) {
        var name: String = "name"
            set(value) {
                field = "$value N"
            }
        var age: Int = -100
    }

    fun User.info() = "$name - $age"

    fun Request.runInThread() {
//        Thread(Runnable {
//            excute()
//        }).start()

        async() {
            val r = excute()
            uiThread {
                sample_text.text = r.city.name
            }
        }

    }

}
