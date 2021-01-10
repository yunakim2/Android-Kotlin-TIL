package com.example.animation_ex

import android.app.ActivityOptions
import android.content.Intent
import android.media.MediaPlayer.create
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair.create
import android.util.Pair

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            // Apply activity transition
//
//
//        } else {
//            // Swap without transition
//
//            val intent = Intent(applicationContext, SecondActivity::class.java)
//            startActivity(intent)
//        }

        main_card.setOnClickListener {
                val intent = Intent(this, SecondActivity::class.java)

                var options : ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    Pair.create(name_txt, "nameTransition"),
                    Pair.create(content_txt, "contentTransition"),
                    Pair.create(profile_im, "imageTransition")
                )
                startActivity(intent, options.toBundle())
            }



    }
}
