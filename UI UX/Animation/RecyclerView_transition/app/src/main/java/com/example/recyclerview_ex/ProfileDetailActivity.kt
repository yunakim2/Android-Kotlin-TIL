package com.example.recyclerview_ex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_profile_detail.*

class ProfileDetailActivity : AppCompatActivity() {
    lateinit var datas : ProfileData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)

        datas = intent.getSerializableExtra("data") as ProfileData

        Glide.with(this).load(datas.img).into(img_profile)
        tv_name.text = datas.name

    }
}