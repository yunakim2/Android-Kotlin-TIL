# Service ,BR, Notification, ContentProvider

## BR(BroadcastReceiver)

> Android Manifest에 정의할 수 있는 4가지 컴포넌트 중 하나.

특정 이벤트 발생시, Android OS나 다른 Android 간의 메시지를 송/수신 하고, 이 메시지를 Intent 객체에 이벤트 정보를 포함하여 전달한다.

- **System Broadcast** : Android OS에서 보내는 메시지로, ACTION_BOOT_COMPLETED, ACTION_POWER_CONNECTED 등이 해당 (Android OS에서 보내는 것)
- **Custom Broadcast** : Android 앱에서 보내는 메시지로, 앱 간의 정보를 교환하는 용도로 사용 (다른 안드로이드 앱에서 보내는 것)

<br>

### Broadcast 보내기

- **Ordered broadcast** : `Context#sendOrderedBroadcast()`
  - 한번에 하나의 리시버에게 전달
  - 리시버는 결과를 다음 리시버에게 전달하거나 방송을 중단할 수 있음
  - 전달되는 순서는 AndroidManifest에 android:priority에 따라 결정 (우선순위 같으면 임의 순서대로)
- **Normal broadcast** : `Context#sendBroadcast()`
  - 정해진 순서 없이 동시에 등록된 모든 리시버에게 전달
  - 메시지 전달방식 중 가장 효과적
  - 리시버는 결과를 다음 리시버에게 전달 X, 방송 중단 X
- ~~**Local broadcast**~~ : `LocalBroadcastManager#sendBroadcast()`
  - 앱 내의 리시버에게만 전달
  - 프로세스간의 통신 X, 보안 이슈X
  - Android 1.1.0 버전 부터 Deprecated 됨

<br>

### BroadcastReceiver를 등록

> Broadcast receiver로 등록하게 되면 System이나 다른 앱에서 보낸 메시지를 intent로 전달받을 수 있다!

- ~~**Static Reciver**~~ : AndroidManifest.xml에 등록하는 방식, BroadcastReceiver를 정적으로 등록
  - `<receiver />`
- **Dynamic Reciver** : Java 파일 내 Context를 통해 BroadcastReceiver를 동적으로 등록하는 방식
  - `Context#registerReceiver()`
  - `Context#unregisterReceiver()`

<br>

### BroadcastReceiver의 제약사항

안드로이드 8.0(API lev 26) 부터 **Static Receiver는 대부분의 System Broadcast를 수신할 수 없다.** -> DynamicReciver를 사용해야함!

<br>

### 구현 방법 - Dynamic BroadcastReceiver

~~~kotlin
- BroadcastReceiver를 상속받는 함수에  onReceive(Context context, Intent intent)에 action에 대한 정의를 통해 특정 action 발생시 방송을 수신할 수 있도록 구현

- 위에서 구현한 CustomReceiver 객체를 onCreate, onResume 에서 특정 filter를 생성하고, 
this.getInstance(this).registerReceiver(mReceiver, filter)
LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filter)를 통해 리시버를 등록한다.


- thisgetInstance(this).unregisterReceiver(mReceiver)
LocalBroadcastManager.getInstance(this).unregisterRecevier(mReceiver); 를 통해 리시버 등록을 해지(onDestory, onPause)
~~~

<br>

### 주의할 점

- 브로드캐스트는 가급적 제한적으로 사용하자 -> 브로드캐스트를 통해 외부에 중요한 정보가 노출되면 보안이 취약해진다.
- 브로드 캐스트 사용 제한 방법
  - LocalBroadcastManager를 이용하여, 앱 내부에만 메시지를 전달하자
  - `Intent#setPackage() API`를 통해 전달되어야하는 앱을 명시하자
  - 송신자 혹은 수신자가 접근 권한을 강제로 하자
    - 송신자 : `Context#sendBroadcast()`를 호출할 때, 권한을 설정 / AndroidManifest.xml에 `<use-permission>` 이 선언된 수신자에게만 전달된다.
    - 수신자 : `Context#registerReceiver()`를 호출할 때, 권한을 설정 / Static Receiver는 `<receiver>`에 `android:permission` 속성으로 설정할 수 있다.

- 브로드 캐스트 리시버 내부에서는 오래걸리는 작업을 수행해서는 안된다. (별도 작업 수행하도록 구현해야 함)

<br>

## Notification

> 앱 외부에서 사용자에게 표시할 수 있는 메시지
>
> 작은 아이콘, 제목, 설명문을 갖는다.

- 상태 표시줄에 표시된다.
- 세부내용을 보려면, 사용자가 알림창을 열어야한다.
- 사용자는 언제든 알림창으로 Notification을 확인할 수 있다.

Notification Channel : Notification 분류, 사용자가 직접 Channel의 소리, 진동 설정을 변경할 수 있는 것

Android 8.0 (API 26) 에서 추가되었고, 알림 채널을 설정하지 않은 Notification은 표시 되지 않음

<br>

 ### Notification Channel 생성

- Channel ID (String)
- Channel 이름 (사용자가 볼 수 있는 채널명) (CharSeq)
- Channel의 중요도 (int)
  - INPORTANCE_NONE(0)~ INPORTANCE_HIGH(4)

~~~kotlin
notificationChannel : NotificationChannel = NotificationChannel(
	CANNEL_ID,
	"MSG",
	NotificationManger.INPORTANCE_DEFAULT
)


// API 26 단말
notification.setPriority(NotifiationCompat.PRIORITY_HIGH)
~~~

*** API 26 이하 단말은 setPriority 를 설정하여 알림의 중요도를 설정할 수 있다.*

<br>

### Notification 생성하기

> Builder 패턴을 사용하여 생성한다.
>
> **NotificationCompat.Builder 사용을 권장함** (하위 OS와의 호환성을 위해)

- setSmallIcon() : 알림 아이콘 (알파가 포함된 흰색 이미지 권장 )
- setContentTitle() : 알림 제목
- setContentText() : 알림 설명문

*** 가급적으로 커스터마이징은 권장하지 않음 (버전에 따라 달라질 수 있으므로)*

### Notification Action 추가하기

`Notification#setContentIntent(PendingIntent)` - 알림을 눌렀을때의 Action 설정

`Notification#addAction(PendingIntent)` - 알림창 안의 특정 Action 버튼 설정

​	ex) 백그라운드 작업같은 스크린 삭제 등 PendingIntent로 설정할 수 있다.

<br>

** **PendingIntent**

> 알림같은 경우에 안드로이드 시스템에서 Intent를 실행시키는 것이므로, 내 앱으로 실행할 수 있게 하려면 권한을 부여 해야 하는데 이때 PendingIntent를 통해 해당 Action 수행 권한을 부여한다.

- `PendingIntent.getActivity()`
- `PendingIntent.getBroadcast()`
- `PendingIntent.getService()`

<br>

### Notification Style

> Notification 의 Style을 설정할 수 있다.

- NotifiactionCompat.BigTextStyle

  기본 View, 확장 View를 구성할 수 있다.  확장 View를 이용하려면, NotificationCompat.BigTextStyle 사용하면 된다.

- NotificationCompat.BigPictureStyle

  큰 이미지와 함께 보여줄 때 사용한다.

- NotificationCompat.MediaStyle

  음악 스트리밍 앱 처럼 Media를 제어할 때 사용

<br>

### Notification 표시, 삭제하기

> System UI 에 Notification을 표시하려면, NotificationManger를 사용해야한다!
>
> NotificationManager는 System Service 중 하나인 NotificationManagerService의 Binder Call로 Notification 객체를 전달하는 역할을 한다.
>
> NotificationManger -> NotificationManagerService -> System UI 순서대로 전달하는 방식

- Notification 전달시 **ID 함께 전달**해야한다.
- 내용 업데이트 시, Notification을 새로 만들어 동일 ID로 `notify()`를 호출한다. 
- 알림 취소시 `cancle()`, `cancleAll()`을 호출한다. 
- `setAutoCancle()`을 사용하면 Notification을 클릭시, Notification이 자동으로 삭제된다.

<br>

### 주의할 점

Android 8.0 (API 26) 부터 Notification 표시 제한 기능이 추가되었다.

짧은 시간 다수의 Notification을 호출시 Notification 업데이트가 되지 않는다.

- 다운로드 상태 표시시, 자주 업데이트하는 것이 아닌  AnimationDrawable을 Small Icon에 사용하거나, setProgress를 사용하여 indeterminate 상태를 보여주자.

- 전화 앱처럼 중요한 알림의 경우 HUN(Heads - up Notification) 으로 표시하며

  - `Notification.Builder#setFullScreenIntent(PendingIntent)`를 사용시, Q 는 uses permission 이 필요하다.

    ~~~kotlin
    <uses -permission android:name = "android.permission.USE_FULL_SCREEN_INTENT" />
    ~~~



<br>

## Service

> 애플리케이션의 4대 컴포넌트 중 하나
>
> Background에서 오래걸리는 작업을 수행할 수 있는 컴포넌트
>
> (화면에서  UI 없이 동작하는 것을 의미 Background Thread XXX)
>
> Ex) 음악 재생, 네트워크 처리, 파일 입출력, DB 작업

- 서비스는 Intent로 시작하며, 사용자가 앱 전환시에도 계속 동작이 가능하다
- 불필요한 리소스를 소모하지 않게 생명주기를 잘 관리해줘야한다.
- 다른앱의 서비스와 함께 사용할수 있다.
- **서비스는 서비스를 호출한 프로세스의 메인 스레드에서 동작한다. (Activity에서 호출시 UI 스레드에서 동작한다.)**
- **서비스를 쓴다고 BG 스레드에서 동작하는 것은 아니다!!**

<br>

### Start Service

`Context#startService()` 로 호출되어 실행된 서비스로, 스스로 멈출 때까지 동작하는 방식

UI를 업데이트 하지 않는다!

~~~kotlin
startService(Intent(this, ExampleService::class.java))
stopService(Intent(this, ExampleService::class.java))



calss ExampleService : Service {
  stopSelf() // 모든 서비스 동작이 끝나면 stopSelf()로 서비스를 종료시킬 수 있음
}
~~~

<br>

### Bind Service

`Context#bindService()`를 호출하여 실행된 서비스로, 서비스와 다른 컴포넌트 간의 데이터를 주고받을 수 있는 Client-Server 인터페이스를 제공하는 방식

Activity등 Client가 요청을 보내고 결과를 가져가며, 모든 Client가 unbind되면 서비스가 멈춤 (onDestory)

~~~kotlin
connection : ServiceConnection = ServiceConnection() {}

bindService(intent, connection, Context.BIND_AUTO_CREATE)
unbindService(connection)
~~~

<br>

** ***Started, Bound Service 모두 UI에 접근할 수 없으나, BroadcastReceiver를 통해 결과를 전달할 수 있다.***

![serviceLifeCycle](../image/serviceLifecycle.png)

### ForgroundService

> 서비스는 백그라운드에서 동작하나 어떤 경우, 사용자가 서비스가 활성화되었는지 알 수 있어야함
>
> ex) Music Service (음악 재생하는 것을 알 수 있어야 한다)

- BackgroundService 보다 **ForgroundService 가 우선순위가 높다** (시스템에 의해 죽지않을 확률이 높다)
- Forground Service는 동작하는 동안 (서비스의 UI를 따로 갖는 것이 아니라) On-going Notification(삭제되지 않고 고정되어진  Notification)을 제공해야한다. 

- ***API 26 부터 시스템이 앱의 BG Service를 자동으로 중지시킨다.***
  - FG Service, BoundService는 해당 XX

<br>

### ~~IntentService (Deprecated)~~

> 서비스의 구현체, 생명주기가 단순화되어 있음
>
> 모든 요청을 WorkThread에서 처리하며, 작업이 완료되면 스스로 중지함

<br>

## ContentProvider

> 데이터를 공유해서 쓴다.
>
> ex) 주소록 DB가 존재할때, 다른 앱에서 DB 정보를 읽어가 사용하는 것
>
> 데이터를 다른 프로세스에게 제공하는 표준 인터페이스
>
> **(단일앱의 데이터 사용하는 경우  ContentProvider를 사용하지 않는 것을 권장한다.)**

### ContentProvider 접근

![스크린샷 2021-07-09 오후 3.36.30](../image/contentProvider.png)

- ContentResolver를 통해 접근을 허용한다.

- UI에서는  ContentProvider에 접근시, Background에서 비동기로 실행해야 Query가 실행되는 동안  UI를 계속 사용할 수 있다.

- ContentProvider의 접근 권한 설정이 가능하다.

  ~~~xml
  <provider
  	android:readPermission = "android.permission.READ_USERS"
  	android:writePermission="android.permission.WRITE_USERS"/>
  ~~~

### ContentProvider Query

- 데이터 읽어올 때 ContentResolver의 query()를 사용한다.
- 콘텐트 URI : 프로바이더 내의 데이터 식별 URI (content://user_dictionary/words)
- _ID 정보를 통해 특정 Row에 접근하는 URI를 생성할 수 있다.
- getContentResolver().query() 로 읽어온 다음 하나씩 읽을 수 있다.
- ContentValues를 활용하여 ContentProvider에 값 추가를 요청할 수 있다.

<br>

### ContentProvider onCreate

** **<u>Applciation의 onCreate 가 호출되기 전에 실행된다.</u>** 

이때, DB 초기화 해야 하는 것이 일반적이며, ContentProvider의 **onCreate()는 MainThread에서 실행되지만**, **다른 메소드들은 일반적으로  Background Thread에서 실행됨**

** Firebase나 WorkManager는 이를 이용하여 ContentProvider로 초기화를 하고 있다.

<br>

### ContentObserver

ContentProvider의 데이터 변경을 알아야하는 경우 사용하며, ContentObserver를 통해 Query 없이 값이 변경되는 것을 알 수 있다. 

***ContentProvider가 Callback을 적절히 호출해야한다!!***



