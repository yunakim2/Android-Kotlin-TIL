package com.example.recyclerview_ex

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_recycler_ex.view.*

class MainActivity : AppCompatActivity() {
    lateinit var profileAdapter: ProfileAdapter
    val datas = mutableListOf<ProfileData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
    }
    private fun initRecycler() {
        profileAdapter = ProfileAdapter(this)
        rv_profile.adapter = profileAdapter
        rv_profile.addItemDecoration(VerticalItemDecorator(20))
        rv_profile.addItemDecoration(HorizontalItemDecorator(10))

        profileAdapter.setOnItemClickListener(object : ProfileAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: ProfileData, pos : Int) {
                Intent(this@MainActivity, ProfileDetailActivity::class.java).apply {
                    putExtra("data", data)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val Pair1 = Pair.create<View,String>(v.img_rv_photo,"image")
                        val Pair2 = Pair.create<View,String>(v.tv_rv_name,"name")
                        val options : ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this@MainActivity, Pair1, Pair2)
                        startActivity(this,options.toBundle())
                    } else {
                        startActivity(this)
                    }
                }
            }

        })


        datas.apply {
            add(ProfileData(img = R.drawable.profile1, name = "mary", age = 24))
            add(ProfileData(img = R.drawable.profile3, name = "jenny", age = 26))
            add(ProfileData(img = R.drawable.profile2, name = "jhon", age = 27))
            add(ProfileData(img = R.drawable.profile5, name = "ruby", age = 21))
            add(ProfileData(img = R.drawable.profile4, name = "yuna", age = 23))

            profileAdapter.datas = datas
            profileAdapter.notifyDataSetChanged()

        }
    }
}