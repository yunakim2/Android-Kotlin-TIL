package com.example.taplayout_usingtabitem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdapter = FragmentAdapter(supportFragmentManager)
        val pager = findViewById<ViewPager>(R.id.ViewPager)
        pager.adapter = pagerAdapter
        val tab = findViewById<TabLayout>(R.id.Tab)
        tab.setupWithViewPager(pager)
    }
}
