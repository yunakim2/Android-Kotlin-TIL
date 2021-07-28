# Manifest 와 Intent

## Manifest

> Application과 관련된 필수적 정보를 정의하는 파일 (xml 형식)

- Package Name : Application **유니크 ID** (ex com.naver.team명)

  -> 동일한 패키지 명은 플레이스토어에 올릴 수 없다.

- Application Component 정의 
  - Activity, Service, Broadcast Receiver, Content Provider
- Appilcaion Permmision : 권한 설정
  - API 23 이상부터 사용자의 권한을 물어봐야함 (Runtime Permission) (targetSDK 버전 26이 권고사항이 됨)
- Device compatibility
  - 전화가 안되는 디바이스가 있을 수 있음
  - `<uses-feature>`로 앱의 필수로 필요한 hardware를 정의할 수 있다 (없으면 앱이 뜨지 않음)

<br>

---

- android:hardwareAccelerated = ["true" | "false"] 
- android:debuggable = ["true"|"false"] : Log.d 들 찍히게 하거나 안찍히게 할거냐 
- Activity 내에서도 name, icon, label, theme, hardwareAccelerated 가 가능하다.
  - Activity와 Application 둘다 theme가 존재하는 경우 Activity theme가 오버라이딩 된다.
- service의 android:exported가 true면 다른앱들이 서비스를 쓸 수 있다.
- receiver 도 service와 동일하다.
- provider는 android:authorities에 일반적으로 package 명이 들어가고 (여러가지 package명 넣어도 괜찮다.)

<br>

### Intent Filter

> 어떤 intent를 호출했을때 filter에 명시된 action이면 해당 컴포넌트가 실행된다.

---

### minSDK와 targetSDK

> 요즘은 manifest가 아닌 buildgradle에 명시한다.

- minSdkVersion : 앱을 사용할 수 있는 최소 앱 API 버전
- targetSdkVersion : 앱의 타겟 API 버전
- compileSdkVersion : compile할때의 API 버전 (호환성이 가능하다.)
  - compileSdkVersion보다 높은 SDK의 API 사용시 빌드가 안된다.

device가 min보다 작으면, 앱 실행 X target보다 크면 target버전으로 앱이 실행됨

compile과 target Sdk 버전은 동일한게 좋다. (compile > target이여도 실행은 된다.)

<br>

## Intent

> Intent는 다른 프로세스간의 통신 (IPC) 이다.
>
> 앱 하나하나 다른 프로세스를 가지고, IPC를 통해 다른 앱과 통신해야한다.
>
> 이때 Binder(Lightweight RPC 메커니즘)을 통해 Intent를 전달한다.

![스크린샷 2021-07-09 오전 11.44.14](../image/intent02.png)

-> Intent는 다른 프로세스에서 전달받은 메시지이다.

- **명시적 인텐트 (Implicit Intent)** : 특정 컴포넌트에  관한 정보를 명시

- **암시적 인텐트 (Explicit Intent)** : 컴포넌트를 명시하지 않는다. 누군가 대신 처리해달라는 것

  - 해당 작업을 처리할 수 있는 모든 앱을 호출하고, 앱이 여러개이면 App Choose를 보여준다.

  - 해당 작업을 처리할 수 있는 앱이 없다면 ActivityNOtFoundException이 발생하므로 앱을 종료한다. 

    > resolveActivity를 호출한 뒤 startActivity를 하자!!

    ~~~java
    Intent sendIntent = new Intent()
    sendIntent.setAction(Intent.ACTION_SEND)
    sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage)
    sendIntent.setType("text/plain")
    
    if(sendIntent.resolveActivity(getPackageManager()) != null) {
    	startActivity(sendIntent)
    }
    ~~~

    

![스크린샷 2021-07-09 오전 11.42.35](../image/intent01.png)

** `Uri` : `sheme`, `host`, `port`, `path`로 이뤄진 하나의 프로토콜화 된 정보

- **pending Intent** (보류중 Intent) : **다른 앱에서 나의 앱 프로세스의 앱 컴포넌트들을 실행하는 것처럼 사용하는 것**

  - Notification에서 특정한 작업 수행시, 선언한 Intent가 실행되도록 하는거 (시스템의 NotificationManager 가 Intent를 실행)

  - App Widget에서 특정한 작업을 수행할 때 (홈 스크린에서 Intent 실행)

  ~~~kotlin
  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0(requestCode),new Intent(MainActivity.this, MainActivity.class), PendingIntent.FLAG_ONE_SHOT )
  ~~~

** *Pending Intent 가 왜 필요할까?!*
시스템에서 제공하는 앱들을 실행할 때, 시스템에서 제공하는 앱은 나의 앱의 동작원리들을 모르기 때문에 PendingIntent안에 동작원리를 넣어주면, 시스템에서 제공하는 앱이 실행시 PendingIntent를 실행하게 된다.

## 실습

Intent 가 잘 수신하는 지 확인하려면 ADB를 이용하면 된다.

~~~
adb shell am start -a <ACTION> -t <MIME_TYPE> -d <DATA> \ -e <EXTRA_NAME> <EXTRA_VALUE> -n <ACTIVITY>
~~~

~~~
adb shell am start -a android.intent.action.DIAL \ -d tel:555-5555 -n org.example.MyApp /.MyActivity
~~~

<br>
Q. 암시적 인텐트 전달받았을 때 현재 백스택을 날리고 MainActivity 하나만 띄울려면?!

​	Intent Flag 중`<activity-alias>` 을 활용하자. 

> FLAG_ACTIVITY_CLEAR_TOP에서 FLAG_ACTIVITY_SINGLE_TOP을 같이 쓰면 A->B->A가 되는데 , A만 남기고 다 지우고싶다면  Activity_alias를 사용하자.

