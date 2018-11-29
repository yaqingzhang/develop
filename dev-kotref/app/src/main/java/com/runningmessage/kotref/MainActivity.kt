package com.runningmessage.kotref

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import com.runningmessage.kotref.kotlin.coroutines.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

/*        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            val index = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
            rootView.section_label.text = getString(R.string.section_format, index)


            if (mapList.size >= index && index > 0) {
                for ((name, apply) in mapList[index - 1]) {
                    if (rootView.text_container is ViewGroup) rootView.text_container.addButton(name, apply)
                }
            }
            return rootView
        }

        private fun ViewGroup.addButton(text: String, click: () -> Any) {
            val btn = TextView(context)
            btn.gravity = Gravity.CENTER
            btn.setBackgroundResource(R.drawable.kt_button_bg)
            btn.text = text
            btn.setMinHeight(90)
            if (this is LinearLayout) {
                val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                lp.gravity = Gravity.CENTER_HORIZONTAL
                lp.bottomMargin = 20
                btn.layoutParams = lp
            }
            btn.setOnClickListener {
                /** Using kotlinx coroutines*/
//                GlobalScope.launch {
//
//                    val result = click.invoke().toString()
//                    val resultText = "$text = $result"
//                    activity?.runOnUiThread { btn.text = resultText }
//                }

                /** Using anko*/
                doAsync {
                    val result = click.invoke().toString()
                    val resultText = "$text = $result"
                    uiThread { btn.text = resultText }
                }


            }
            addView(btn)
        }

        companion object {

            val mapList: ArrayList<LinkedHashMap<String, () -> Any>>
                get() {

                    val map1 = LinkedHashMap<String, () -> Any>()

                    map1["Basic.Companion::t01"] = Basic.Companion::t01
                    map1["Basic.Companion::t02"] = Basic.Companion::t02
                    map1["Basic.Companion::t03"] = Basic.Companion::t03
                    map1["Basic.Companion::t04"] = Basic.Companion::t04
                    map1["Basic.Companion::t05"] = Basic.Companion::t05
                    map1["Basic.Companion::t06"] = Basic.Companion::t06

                    map1["CancelTimeout.Companion::t01"] = CancelTimeout.Companion::t01
                    map1["CancelTimeout.Companion::t02"] = CancelTimeout.Companion::t02
                    map1["CancelTimeout.Companion::t03"] = CancelTimeout.Companion::t03
                    map1["CancelTimeout.Companion::t04"] = CancelTimeout.Companion::t04
                    map1["CancelTimeout.Companion::t05"] = CancelTimeout.Companion::t05

                    map1["Channels.Companion.t01"] = Channels.Companion::t01
                    map1["Channels.Companion.t02"] = Channels.Companion::t02
                    map1["Channels.Companion.t03"] = Channels.Companion::t03
                    map1["Channels.Companion.t04"] = Channels.Companion::t04
                    map1["Channels.Companion.t05"] = Channels.Companion::t05
                    map1["Channels.Companion.t06"] = Channels.Companion::t06
                    map1["Channels.Companion.t07"] = Channels.Companion::t07
                    map1["Channels.Companion.t08"] = Channels.Companion::t08
                    map1["Channels.Companion.t09"] = Channels.Companion::t09
                    map1["Channels.Companion.t10"] = Channels.Companion::t10

                    map1["ComSusFun.Companion.t01"] = ComSusFun.Companion::t01
                    map1["ComSusFun.Companion.t02"] = ComSusFun.Companion::t02
                    map1["ComSusFun.Companion.t03"] = ComSusFun.Companion::t03
                    map1["ComSusFun.Companion.t04"] = ComSusFun.Companion::t04
                    map1["ComSusFun.Companion.t05"] = ComSusFun.Companion::t05
                    map1["ComSusFun.Companion.t05"] = ComSusFun.Companion::t05
                    map1["ComSusFun.Companion.t051"] = ComSusFun.Companion::t051

                    map1["ContextDispatchers.Companion.t01"] = ContextDispatchers.Companion::t01
                    map1["ContextDispatchers.Companion.t02"] = ContextDispatchers.Companion::t02
                    map1["ContextDispatchers.Companion.t03"] = ContextDispatchers.Companion::t03
                    map1["ContextDispatchers.Companion.t04"] = ContextDispatchers.Companion::t04
                    map1["ContextDispatchers.Companion.t05"] = ContextDispatchers.Companion::t05
                    map1["ContextDispatchers.Companion.t06"] = ContextDispatchers.Companion::t06
                    map1["ContextDispatchers.Companion.t07"] = ContextDispatchers.Companion::t07
                    map1["ContextDispatchers.Companion.t08"] = ContextDispatchers.Companion::t08
                    map1["ContextDispatchers.Companion.t09"] = ContextDispatchers.Companion::t09
                    map1["ContextDispatchers.Companion.t10"] = ContextDispatchers.Companion::t10
                    map1["ContextDispatchers.Companion.t11"] = ContextDispatchers.Companion::t11

                    map1["ExceptionHandling.Companion.t01"] = ExceptionHandling.Companion::t01
                    map1["ExceptionHandling.Companion.t02"] = ExceptionHandling.Companion::t02
                    map1["ExceptionHandling.Companion.t03"] = ExceptionHandling.Companion::t03
                    map1["ExceptionHandling.Companion.t031"] = ExceptionHandling.Companion::t031
                    map1["ExceptionHandling.Companion.t04"] = ExceptionHandling.Companion::t04
//                    map1["ExceptionHandling.Companion.t05"] = ExceptionHandling.Companion::t05
//                    map1["ExceptionHandling.Companion.t06"] = ExceptionHandling.Companion::t06
//                    map1["ExceptionHandling.Companion.t07"] = ExceptionHandling.Companion::t07
//                    map1["ExceptionHandling.Companion.t08"] = ExceptionHandling.Companion::t08
//                    map1["ExceptionHandling.Companion.t09"] = ExceptionHandling.Companion::t09
//                    map1["ExceptionHandling.Companion.t10"] = ExceptionHandling.Companion::t10
//                    map1["ExceptionHandling.Companion.t11"] = ExceptionHandling.Companion::t11
//                    map1["ExceptionHandling.Companion.t12"] = ExceptionHandling.Companion::t12
//                    map1["ExceptionHandling.Companion.t13"] = ExceptionHandling.Companion::t13
//                    map1["ExceptionHandling.Companion.t14"] = ExceptionHandling.Companion::t14
//                    map1["ExceptionHandling.Companion.t15"] = ExceptionHandling.Companion::t15
//                    map1["ExceptionHandling.Companion.t16"] = ExceptionHandling.Companion::t16
//                    map1["ExceptionHandling.Companion.t17"] = ExceptionHandling.Companion::t17
//                    map1["ExceptionHandling.Companion.t18"] = ExceptionHandling.Companion::t18
//                    map1["ExceptionHandling.Companion.t19"] = ExceptionHandling.Companion::t19

                    val list = ArrayList<LinkedHashMap<String, () -> Any>>()
                    list.add(map1)
                    return list
                }

            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}