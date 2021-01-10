package com.example.coordinatortablayout


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewPager()
    }

    fun initViewPager()
    {
        val pagerAdapter = FragmentAdapter(supportFragmentManager)
        val pager = findViewById<ViewPager>(R.id.viewpager)
        val tab = findViewById<TabLayout>(R.id.tab)
        pager.adapter = pagerAdapter

        val red = ContextCompat.getColor(this, R.color.colorRed)
        val orange = ContextCompat.getColor(this,R.color.colorOrange)
        val blue = ContextCompat.getColor(this,R.color.colorBlue)
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            @SuppressLint("ResourceAsColor")
            override fun onTabSelected(p0: TabLayout.Tab?) {
               when(p0!!.position)
               {
                   0-> {
                       lab3_appbar_image.setImageResource(R.mipmap.one)
//                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
                       lab3_collapsing.setBackgroundColor(orange)
                       lab3_collapsing.setContentScrimColor(orange)
                       lab3_collapsing.setStatusBarScrimColor(orange)

                   }
                   1-> {
                       lab3_appbar_image.setImageResource(R.mipmap.two)
//                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
                       lab3_collapsing.setBackgroundColor(red)
                       lab3_collapsing.setContentScrimColor(red)
                       lab3_collapsing.setStatusBarScrimColor(red)

                   }
                   2-> {
                       lab3_appbar_image.setImageResource(R.mipmap.three)
//                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
                       lab3_collapsing.setBackgroundColor(blue)
                       lab3_collapsing.setContentScrimColor(blue)
                       lab3_collapsing.setStatusBarScrimColor(blue)

                   }
                   else-> {
                       lab3_appbar_image.setImageResource(R.mipmap.one)
//                       lab3_collapsing.setContentScrimColor(R.color.colorAccent)
                       lab3_collapsing.setBackgroundColor(blue)
                       lab3_collapsing.setContentScrimColor(blue)
                       lab3_collapsing.setStatusBarScrimColor(blue)
                   }
               }
            }

        })


        tab.setupWithViewPager(pager)
    }




}
