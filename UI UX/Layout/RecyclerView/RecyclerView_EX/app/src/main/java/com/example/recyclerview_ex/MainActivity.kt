package com.example.recyclerview_ex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

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