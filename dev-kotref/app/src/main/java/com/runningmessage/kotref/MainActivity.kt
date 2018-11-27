package com.runningmessage.kotref

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import com.runningmessage.kotref.kotlin.coroutines.Basic
import com.runningmessage.kotref.kotlin.coroutines.CancelTimeout
import com.runningmessage.kotref.kotlin.coroutines.Channels
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

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

                GlobalScope.launch {

                    val result = click.invoke().toString()
                    val resultText = "$text = $result"
                    activity?.runOnUiThread { btn.text = resultText }
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
