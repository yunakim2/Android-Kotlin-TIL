package com.example.taplayout_usingtabitem

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val frag = when(position)
        {
            0-> Tab1Fragment().newInstant()
            1-> Tab2Fragment().newInstant()
            else -> Tab1Fragment().newInstant()
        }
        return frag
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position)
        {
            0->"Orange"
            1->"Green"
            else->"Orange"
        }

        return title
    }
    override fun getCount(): Int = 2
}