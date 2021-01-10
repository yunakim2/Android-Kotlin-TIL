package com.example.coordinatortablayout

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter ( fm : FragmentManager): FragmentPagerAdapter(fm) {
  //position 에 따라 원하는 Fragment로 이동시키기
  override fun getItem(position: Int): Fragment {
       val fragment =  when(position)
       {
           0->OneFragment().newInstant()
           1-> TwoFragment().newInstant()
           2-> ThreeFragment().newInstant()
           else -> OneFragment().newInstant()
       }
        return fragment
    }

  //tab의 개수만큼 return
    override fun getCount(): Int = 3

  //tab의 이름 fragment마다 바꾸게 하기
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