package com.example.taplayout1

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import javax.xml.xpath.XPathFactory.newInstance

class FragmentAdapter (fm : FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
       val fragment =  when(position)
       {
           0->tab1Fragment().newInstant()
           1-> tab2Fragment().newInstant()
           2-> tab3Fragment().newInstant()
           else -> tab1Fragment().newInstant()
       }
        return fragment
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position)
        {
            0->"0ne"
            1->"Two"
            2->"Three"
            else -> "main"
        }
        return title     }
}