# Zxing 라이브러리를 이용한 qr코드 스캐너

<br>

👉🏻 [Zxing 라이브러리 주소](https://github.com/journeyapps/zxing-android-embedded)

<br>

✔️ **build,gradle 라이브러리 추가하기**

>  👉🏻 sdk version 24 미만일때도 사용 가능

~~~kotlin
  	implementation('com.journeyapps:zxing-android-embedded:4.1.0') { transitive = false }
    implementation 'androidx.appcompat:appcompat:1.0.2'
    implementation 'com.google.zxing:core:3.3.0'
~~~

<br>

> 👉🏻 sdk version 24 이상 

~~~kotlin
  implementation 'com.journeyapps:zxing-android-embedded:4.1.0'
  implementation 'androidx.appcompat:appcompat:1.0.2'
~~~

<br>

✔️ **권한 설정**

~~~kotlin
<uses-sdk tools:overrideLibrary="com.google.zxing.client.android" />

<uses-permission android:name="android.permission.CAMERA" />
~~~

<br>

‼️ **qr코드 스캐너 카메라 세로모드 적용**

~~~kotlin
<activity android:name="com.journeyapps.barcodescanner.CaptureActivity"
	android:screenOrientation="fullSensor"
	tools:replace="screenOrientation"/>
~~~

<br>

‼️ **qr코드 호출할 activity안에 작성하기**

~~~kotlin
<activity
	android:name=".QrcodeScanActivity"
	android:screenOrientation="portrait"
	android:stateNotNeeded="true"
	android:windowSoftInputMode="stateAlwaysHidden" />
~~~



---

✔️ **사용법**

```kotlin
private fun initQRcodeScanner() {
    val integrator  = IntentIntegrator(this)
    integrator.setBeepEnabled(false)
    integrator.setOrientationLocked(true)
    integrator.setPrompt("QR코드를 인증해주세요.")
    integrator.initiateScan()
}
```

👉🏻 setPrompt -> 스캐너 밑에 문구 지정

<br>

~~~kotlin
// qr코드 주소 반환 시에
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result !=null) {
            if(result.contents == null) {
                // qr코드에 주소가 없거나, 뒤로가기 클릭 시
                var toast = Toast(this )
                toast.drawCustomToast("QR코드 인증이 취소되었습니다.")
                finish()
            } else {
                //qr코드에 주소가 있을때 -> 주소에 관한 dialog 띄우는 함수 호출
                initAddressDialog(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
~~~

<br>

<br>