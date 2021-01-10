# WIFI 연결상태 확인하기

<br>

> 권한 설정

~~~kotlin
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
~~~

<br>

> 사용법

~~~kotlin
// 인터넷 연결 확인 함수
fun getNetworkConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
    val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

    return isConnected

}
~~~

✔️ wifi&셀룰러 연결시 **true 반환**

✔️ wifi&셀룰러 연결시 **false 반환**

