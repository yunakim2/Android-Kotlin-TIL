# WebView 



✔️ **사용법**

> 권한 설정해주기

~~~kotlin
<uses-permission android:name="android.permission.INTERNET" />
~~~

<br>

> 웹뷰 띄우기

~~~kotlin
private fun initWebView(address : String) {
  val webView = findViewById<WebView>(R.id.webView)

  //와이파이 & 데이터 연결되어 있으면 웹뷰 생성
  if(getNetworkConnected(applicationContext) ) {
    //자바 스크립트 허용
    webView.settings.javaScriptEnabled = true
    // 웹뷰안에 새 창이 뜨지 않도록 방지
    webView.webViewClient = WebViewClient()
    webView.webChromeClient = WebChromeClient()
    webView.loadUrl(address)
  } else {
    var toast = Toast(this)
    toast.drawCustomToast("인터넷 연결 상태를 확인해주세요.")
    finish()
  }

}
~~~

<br>

> 웹뷰 뒤로가기 이벤트

~~~kotlin
override fun onBackPressed() {
  //웹사이트에서 뒤로 갈 페이지 존재시
  if(webView.canGoBack()) {
    webView.goBack() // 웹사이트 뒤로가기
  }
  else {
    super.onBackPressed()
  }
}
~~~

<br>

<br>