package id.amoled.timerapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.amoled.timerapp.ergonomic.Ergonomic1Fragment
import id.amoled.timerapp.ergonomic.Ergonomic2Fragment

class ErgonomicPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    // sebuah list yang menampung objek Fragment
    private val pages = listOf(
        Ergonomic1Fragment(),
        Ergonomic2Fragment()
    )

    // menentukan fragment yang akan dibuka pada posisi tertentu
    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    // judul untuk tabs
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Setiap 20 Menit"
            else -> "Setiap 2 Jam"
        }
    }
}