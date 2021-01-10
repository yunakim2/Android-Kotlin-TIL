package com.example.qrcode_scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_qrcode_scanner.setOnClickListener {
            val token = ""
            val intent = Intent(applicationContext, QrcodeScanActivity::class.java)
            intent.putExtra("token",token)
            startActivity(intent)
        }
    }
}