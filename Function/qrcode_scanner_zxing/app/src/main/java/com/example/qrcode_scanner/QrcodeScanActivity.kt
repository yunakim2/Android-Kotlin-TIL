package com.example.qrcode_scanner

import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_qrcode_scan.*
import kotlinx.android.synthetic.main.custom_address_dialog.view.*

class QrcodeScanActivity : AppCompatActivity() {
    private lateinit var token : String
    private lateinit var uuid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scan)

        val actionBar = supportActionBar
        actionBar?.hide()

        token = intent.getStringExtra("token")

        //기기 고유 uuid 생성하기
        uuid =  Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
        //Log.d("uuid",uuid)

        initQRcodeScanner()

        btn_webview.setOnClickListener {
            finish()
        }

    }

    //QRcode 스캔 카메라 호출 함수
    private fun initQRcodeScanner() {
        val integrator  = IntentIntegrator(this)
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(true)
        integrator.setPrompt("QR코드를 인증해주세요.")
        integrator.initiateScan()
    }

    // 웹페이지를 띄우는 함수
    private fun initWebView(address : String) {
        val webView = findViewById<WebView>(R.id.webView)

        if(getNetworkConnected(applicationContext) ) {
            //자바 스크립트 허용
            webView.settings.javaScriptEnabled = true
            // 웹뷰안에 새 창이 뜨지 않도록 방지
            webView.webViewClient = WebViewClient()
            webView.webChromeClient = WebChromeClient()
            webView.loadUrl(address)
        } else {
            var toast = Toast(this )
            toast.drawCustomToast("인터넷 연결 상태를 확인해주세요.")
            finish()
        }

    }

    //뒤로가기 버튼 이벤트
    override fun onBackPressed() {
        //웹사이트에서 뒤로 갈 페이지 존재시
        if(webView.canGoBack()) {
            webView.goBack() // 웹사이트 뒤로가기
        }
        else {
            super.onBackPressed()
        }
    }

    //qr코드에서 반환된 주소에 관한 dialog 띄우는 함수
    private fun Toast.initQrcodeToast(content : String) {
        val inflater : LayoutInflater = this@QrcodeScanActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(
            R.layout.custom_address_dialog,this@QrcodeScanActivity.findViewById(
                R.id.cl_custom_address_toast))
        val btnAddress = layout.findViewById<TextView>(R.id.btn_qrcode_address)
        btnAddress.text = content
        btnAddress.setOnClickListener {
            initWebView(content)
        }
        setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL,0,0)
        duration = Toast.LENGTH_LONG
        view = layout
        show()


    }

    // qr코드 주소 반환 시에
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        var toast = Toast(this )
        if(result !=null) {
            if(result.contents == null) {
                // qr코드에 주소가 없거나, 뒤로가기 클릭 시
                toast.drawCustomToast("QR코드 인증이 취소되었습니다.")
                finish()
            } else {
                //qr코드에 주소가 있을때 -> 주소에 관한 Toast 띄우는 함수 호출
                toast.initQrcodeToast(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    // qr코드에 주소가 없거나, 뒤로가기 클릭 시 뜨는 toast 함수
    private fun Toast.drawCustomToast(content : String){
        val inflater : LayoutInflater = this@QrcodeScanActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(
            R.layout.custom_address_dialog,this@QrcodeScanActivity.findViewById(
                R.id.cl_custom_address_toast))
        val btnAddress = layout.findViewById<TextView>(R.id.btn_qrcode_address)
        btnAddress.text = content
        setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL,0,0)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }

}

