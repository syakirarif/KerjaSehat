package id.amoled.timerapp.onboarding

import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import id.amoled.timerapp.R
import kotlinx.android.synthetic.main.activity_pager.*
import kotlinx.android.synthetic.main.fragment_pager.view.*
import androidx.core.content.ContextCompat
import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import id.amoled.timerapp.util.OnboardingUtil
import android.widget.ImageView
import id.amoled.timerapp.KeduaActivity

class PagerActivity : AppCompatActivity() {

    /**
     * The [androidx.viewpager.widget.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * androidx.fragment.app.FragmentStatePagerAdapter.
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    var page = 0
    private lateinit var indicators: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        val color1 = ContextCompat.getColor(this, R.color.grey)
        val color2 = ContextCompat.getColor(this, R.color.grey)
        val color3 = ContextCompat.getColor(this, R.color.grey)

        val colorList = intArrayOf(color1, color2, color3)

        val evaluator = ArgbEvaluator()

        indicators = arrayOf(intro_indicator_0, intro_indicator_1, intro_indicator_2)

        //setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val colorUpdate = evaluator.evaluate(
                    positionOffset,
                    colorList[position],
                    colorList[if (position == 2) position else position + 1]
                ) as Int
                container.setBackgroundColor(colorUpdate)
            }

            override fun onPageSelected(position: Int) {
                page = position
                updateIndicators(page)
//                when (position) {
//                    0 -> container.setBackgroundColor(color1)
//                    1 -> container.setBackgroundColor(color2)
//                    2 -> container.setBackgroundColor(color3)
//                }
                intro_btn_skip.visibility = if (position == 2) View.GONE else View.VISIBLE
                intro_btn_finish.visibility = if (position == 2) View.VISIBLE else View.GONE
                intro_btn_next.visibility = if (position == 2) View.GONE else View.VISIBLE

            }

        })

        intro_btn_next.setOnClickListener {
            page += 1
            container.setCurrentItem(page, true)
        }

        intro_btn_skip.setOnClickListener {
            finish()
            OnboardingUtil.saveSharedSetting(applicationContext, KeduaActivity.PREF_USER_FIRST_TIME, "false")
        }

        intro_btn_finish.setOnClickListener {
            finish()
            OnboardingUtil.saveSharedSetting(applicationContext, KeduaActivity.PREF_USER_FIRST_TIME, "false")
        }


    }

    fun updateIndicators(position: Int) {
        for (i in 0 until indicators.size) {
            indicators[i].setBackgroundResource(
                if (i == position) R.drawable.indicator_selected else R.drawable.indicator_unselected
            )
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_pager, menu)
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

        @SuppressLint("SetTextI18n")
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_pager, container, false)
//            rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))

            val position = arguments?.getInt(ARG_SECTION_NUMBER)

            when(position){
                1 -> {
                    rootView.onboarding_tv_title.text = getString(R.string.ob_title_1)
                    rootView.onboarding_tv_desc.text = getString(R.string.ob_desc_1)
                    rootView.onboarding_animation.setAnimation("onboarding_office.json")
                }
                2 -> {
                    rootView.onboarding_tv_title.text = getString(R.string.ob_title_2)
                    rootView.onboarding_tv_desc.text = getString(R.string.ob_desc_2)
                    rootView.onboarding_animation.setAnimation("onboarding_read.json")
                }
                3 -> {
                    rootView.onboarding_tv_title.text = getString(R.string.ob_title_3)
                    rootView.onboarding_tv_desc.text = getString(R.string.ob_desc_3)
                    rootView.onboarding_animation.setAnimation("onboarding_alert.json")
                }
            }

            return rootView
        }

        companion object {
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
