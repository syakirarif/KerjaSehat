package id.amoled.timerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.amoled.timerapp.ergonomic.ErgonomicActivity
import id.amoled.timerapp.onboarding.PagerActivity
import id.amoled.timerapp.util.OnboardingUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var isUserFirstTime: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isUserFirstTime =
            java.lang.Boolean.valueOf(
                OnboardingUtil.readSharedSetting(this@MainActivity,
                    KeduaActivity.PREF_USER_FIRST_TIME, "true"))

        val introIntent = Intent(this@MainActivity, PagerActivity::class.java)
        introIntent.putExtra(KeduaActivity.PREF_USER_FIRST_TIME, isUserFirstTime)

        if (isUserFirstTime)
            startActivity(introIntent)

        btn_pengertian_mds.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        btn_penyakit_kerja.setOnClickListener {
            val intent = Intent(this, PenyakitKerjaActivity::class.java)
            startActivity(intent)
        }

        btn_ergonomic.setOnClickListener {
            val intent = Intent(this, ErgonomicActivity::class.java)
            startActivity(intent)
        }

        btn_postur.setOnClickListener {
            val intent = Intent(this, PosturActivity::class.java)
            startActivity(intent)
        }

        btn_petunjuk.setOnClickListener {
            val intent = Intent(this, PetunjukActivity::class.java)
            startActivity(intent)
        }

        fab_mulai.setOnClickListener{
            val intent = Intent(this, KeduaActivity::class.java)
            startActivity(intent)
        }

    }
}
