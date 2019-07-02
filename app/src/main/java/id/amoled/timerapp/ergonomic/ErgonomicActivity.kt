package id.amoled.timerapp.ergonomic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.amoled.timerapp.R
import id.amoled.timerapp.adapter.ErgonomicPagerAdapter
import kotlinx.android.synthetic.main.activity_ergonomic.*

class ErgonomicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ergonomic)

        viewpager_ergonomic.adapter = ErgonomicPagerAdapter(supportFragmentManager)
        tabs_ergonomic.setupWithViewPager(viewpager_ergonomic)
    }
}
