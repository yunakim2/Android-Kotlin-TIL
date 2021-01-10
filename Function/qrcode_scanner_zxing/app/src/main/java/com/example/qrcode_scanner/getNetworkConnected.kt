package com.example.qrcode_scanner

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

// 인터넷 연결 확인 함수
fun getNetworkConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
    val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

    return isConnected

}