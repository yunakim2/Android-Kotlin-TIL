# Android-Kotlin-TIL - Function
🙂 그동안 개발 프로젝트를 진행하면서 구현했던 기능들 정리 모음집 입니다 🙃

<br>

- **목차**
  - [✏️ Wifi 연결 상태 확인하기](#Wifi-연결-상태-확인하기)
  - [🛠 WebView 사용하기](#WebView-사용하기)
  - [💫 Splash 화면 만들기](#Splash-화면-만들기)
  - [🦹🏻 darkmode 적용하기](#darkmode-적용하기)
  - [📝 언어 설정하기(Eng / Kor)](#언어-설정하기(Eng-/-Kor))
  - [🥕 Zxing 라이브러리로 QR코드 스캐너 만들기](#Zxing-라이브러리로-QR코드-스캐너-만들기)
  - [📌 RecyclerView 클릭 이벤트 외부에 만들기](#RecyclerView-클릭-이벤트-외부에-만들기)

<br>

## Wifi 연결 상태 확인하기

[Wifi 연결 상태 확인하기 포스팅📌](https://yunaaaas.tistory.com/9?category=966970)

~~~kotlin
fun getNetworkConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
    val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

    return isConnected

}
~~~

<br>

## WebView 사용하기

[WebView 사용하기 포스팅📌](https://yunaaaas.tistory.com/11?category=966970)

~~~kotlin
private fun initWebView(address : String) {

  val webView = findViewById<WebView>(R.id.webView)

  // 와이파이 & 데이터 연결되어 있으면 웹뷰 생성
  if(getNetworkConnected(applicationContext) ) {
    
    // 인터넷 연결 되어 있을 때 (셀룰러/와이파이)
    webView.settings.javaScriptEnabled = true // 자바 스크립트 허용
    
    // 웹뷰안에 새 창이 뜨지 않도록 방지
    webView.webViewClient = WebViewClient() 
    webView.webChromeClient = WebChromeClient()
    
    // 원하는 주소를 WebView에 연결
    webView.loadUrl(address) 
  } else {
    // 인터넷 연결 되어 있지 않을 때 (셀룰러/와이파이)
    var toast = Toast(this)
    toast.drawCustomToast("인터넷 연결 상태를 확인해주세요.")
    finish() // Activity 종료
  }

}
~~~

<br>

## Splash 화면 만들기

[Splash 화면 만들기 포스팅📌](https://yunaaaas.tistory.com/17?category=966970)

<p align="center">
  <img src = "https://blog.kakaocdn.net/dn/bXa0pX/btqR6u9MVvP/gX4zpkCH9VPozDe9V8u3Y1/img.gif" alt="splash" height="500px" />
</p>



<br>

## darkmode 적용하기

[darkmode 적용하기 포스팅📌](https://yunaaaas.tistory.com/18?category=966970)

<p align="center">
  <img src = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fn8eQp%2FbtqR9mwSaJc%2FVkdR6kOUhktPsfpNFTwz5K%2Fimg.gif" alt="splash" height="500px" />
</p>

<br>

## 언어 설정하기(Eng / Kor)

[언어 설정 적용하기 포스팅📌](https://yunaaaas.tistory.com/19?category=966970)

~~~kotlin
private lateinit var configuration: Configuration

configuration.locale = Locale.KOREA
configuration.locale = Locale.US


resources.updateConfiguration(configuration,resources.displayMetrics)

~~~

<br>

## Zxing 라이브러리로 QR코드 스캐너 만들기

[Zxing 라이브러리 관련 포스팅📌](https://yunaaaas.tistory.com/10?category=966969)

<p align="center">
  <img src = "https://user-images.githubusercontent.com/45454585/104130474-44e4dc00-53b4-11eb-9369-65c8b04f14a3.jpg" alt="qrcode2" height="500px" />
  <img src = "https://user-images.githubusercontent.com/45454585/104130476-46ae9f80-53b4-11eb-9e35-211543446e27.jpg" alt="qrcode3" height="500px" />
</p>





## RecyclerView 클릭 이벤트 외부에 만들기

[RecyclerView 클릭 이벤트 관련 포스팅📌](https://yunaaaas.tistory.com/57)

<p align="center">
  <img src = "https://user-images.githubusercontent.com/45454585/105381918-dffe7100-5c52-11eb-8f66-e1e8d9a450ad.gif" alt="splash" height="500px" />
</p>

