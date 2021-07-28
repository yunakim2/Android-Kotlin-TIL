# Activity 와 Activity Stack의 이해

### Activity 란?

> 안드로이드 4대 컴포넌트 중 하나로, 화면에 보여주는 UI를 구성하는 컴포넌트

<br>

## Introducation to activities

- 액티비티를 manifest에 추가하여 사용한다.

  이때, activity 테그 안에 intent-filter는 해당 액티비티가 열리는 action을 정의할 수 있다.

~~~kotlin
<activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
</activity>
~~~

- 액티비티들은 호출하면 Stack에 쌓이게 된다. 그래서 다른 액티비티로 전환시에 기존 액티비티가 사라지는 것이 아닌 back button을 누르면 기존 액티비티가 보여지게 된다!

<br>

## How to use 'Logcat'

 `Log._(Tag명(String), 원하는 String 값(String))`

- `Log.e` 오류
- `Log.w` 경고
- `Log.i` 정보
- `Log.d` 디버그 ->  *출시하면 debug용이기 때문에 사라지므로 주로 사용!*
- `Log.v` 상세

<br>

## Activity Lifecycle

![activityLifecycle](https://media.oss.navercorp.com/user/25548/files/2fc8cd00-e026-11eb-9a7f-0c417067bf00)

- <kbd>App process Killed</kbd>

  안드로이드에서 여러개의 앱을 실행할 경우 앱 내의 메모리가 부족하여 kill 될 수 있다!

  이때, *앱이 kill 되는 경우 사용자의 현재 정보를 저장해놓으려면 ?!* `onDestory()` 가 아닌 **`onPause()` 에서 저장**해야한다.

<br>

![스크린샷 2021-07-08 오전 11 24 43](https://media.oss.navercorp.com/user/25548/files/3eaf7f80-e026-11eb-9ecd-eff02570c0a0)

- Activity 전체 Lifetime : `onCreate()` ~ `onDestory()`
- Visible Lifetime (사용자의 눈에 보이는 Lifecycle) : `onStart()` ~ `onStop()`
- Foreground Lifetime (사용자가 앱을 실제로 쓰고 있을 때의 Lifecycle) : `onResume()` ~ `onPause()`



 ## Lifecycle 콜백 메서드들에 Log 찍어 확인해보기

~~~kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("ActivityLifeCycle", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("ActivityLifeCycle", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifeCycle", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ActivityLifeCycle", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ActivityLifeCycle", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActivityLifeCycle", "onDestroy")
    }
}
~~~

- 앱 시작할 때 : `onCreate()` ~ `onResume()`

  ![스크린샷 2021-07-08 오후 12 02 29](https://media.oss.navercorp.com/user/25548/files/48d17e00-e026-11eb-8efa-5b69e9d8a32d)

- 앱 종료할 때 : `onPause()` ~ `onDestory()`

![스크린샷 2021-07-08 오후 12 03 16](https://media.oss.navercorp.com/user/25548/files/3d328700-e027-11eb-81fb-075e66bd55e7)

- 화면 회전할 때 : `onPause()` ~ `onResume()`

  ![스크린샷 2021-07-08 오후 12 03 16](https://media.oss.navercorp.com/user/25548/files/5555d680-e026-11eb-9d2b-776ed556b0fc)

- Home 키로 나갔다가 돌아올 때 : `onPause()` ~ `onResume()`

  ![스크린샷 2021-07-08 오후 12 06 10](https://media.oss.navercorp.com/user/25548/files/5e46a800-e026-11eb-9f48-6e7acdb55de3)

- 다른 앱과 멀티 윈도우 사용할 때 : 다른 앱을 선택시 현재 기준의 앱은 `포그라운드(Foreground) 상태`가 되고 `onResume()`이 된다. 다른 앱을 선택시, `onPause()` 상태가 되지만 **Visible** 하다.

![스크린샷 2021-07-08 오후 12 23 42](https://media.oss.navercorp.com/user/25548/files/74546880-e026-11eb-8a21-77b9986c452c)

- ***Q. 네이버 앱을 쓰면서 네이버 동영상을 틀고 싶을 때, 동영상 재생, 끄는 행위를 `onStart`, `onStop`에서 해야될까? `onResume`, `onPause`에서 해야될까?!***

  >  **`onStart`, `onStop`에서 하는게 더 좋다!**
  >
  > 다른 앱을 사용시, `onResume`, `onPause`에서 동영상을 끄거나 재생하면 자꾸 동영상이 멈춰서 불편해진다.

- 다이얼로그가 실행되면 어떻게 될까?

  > onPause가 뜨게 된다.
  >
  > 다이얼로그가 화면에 보여지므로, onPause가 실행된다.

<br>

## Activity state changes

> 정보를 언제 저장하는지는 매우 중요하다!
>
> App process Killed가 호출되는 시점에 데이터를 저장할 수 있어야한다.

- **State 의 저장** : `onSaveInstanceState()`

  - 앱을 종료시에는 불러지지 않지만 화면 회전이나 어떤 문제상황(Home으로 갔을때 , 등)에서 불러진다.

    -> 다시 앱을 실행시에 계속 상태를 복원하기 위해 불러진다.

- **State의 복원** : `onCreate()` or `onRestoreInstanceState()`

  - `onCreate`는 액티비티가 생성되었을 때 맨처음 호출되는 함수로 이때 `savedInstanceState`는 **Null** 이다.

    -> **화면 회전의 경우** `onCreate`부터 다시 호출되므로 **null 이 아닌 경우 조건을 통해 현재 상태를 불러 올 수 있다.**

  - `onRestoreInstanceState()`는 `saveInstanceState`가 **Null 이 아닌 경우에만 호출된다.**

![스크린샷 2021-07-08 오후 1 36 10](https://media.oss.navercorp.com/user/25548/files/7fa79400-e026-11eb-9f5b-66f173e2a9ba)

![스크린샷 2021-07-08 오후 1 40 16](https://media.oss.navercorp.com/user/25548/files/9352fa80-e026-11eb-885e-85bea3bf875e)

### 중요한 점 🔥🔥🔥

-  사용자가 앱을 처음 실행할거나 앱을 종료할 때는 `onSaveInstanceState` 와 `onRestoreInstanceState`가 불리지 않는다!

  - State 저장하는 이유는 현재 상태를 저장하는 것이기 때문이다.

    반면, **화면회전의 경우**에는 화면이 회전되고 **다시 화면을 그리는데 그때 회전하기 전의 상태를 그대로 보여줘야하기 때문에 <u>State 저장이 필요하다.</u>**

- `onRestoreInstanceState`의 파라미터값은 **NonNull**로 즉, `saveInstanceState`에 저장된 값이 없을 경우 호출되지 않는다.

- **`EditText` 에 입력된 값은 따로 `State` 저장없이 다시 불러지게 되는데** 이 이유는 `EditText`안의  `onSaveInstanceState()` 가 불러지기 때문이다. 🔥 ***xml에서 id값을 꼭 명시해주자*** 

+) 꿀팁 검색시 shift 두번을 누르면, Android 프레임워크 소스까지 찾을 수 있게 된다 : )

![스크린샷 2021-07-08 오후 1 50 40](https://media.oss.navercorp.com/user/25548/files/a1088000-e026-11eb-9705-b2fa73240fbf)

`Cmd + f12` 를 누르면 해당 파일의 메소드를 다 볼 수 있다!

![스크린샷 2021-07-08 오후 1 54 36](https://media.oss.navercorp.com/user/25548/files/ac5bab80-e026-11eb-8bf3-7e6ab0ee5479)

`Cmd + p` 를 누르면 메소드의 파라미터들을 모두 확인 할 수 있다.

`Cmd + b` 를 누르면 해당 파일로 이동

`option + f7` 를 누르면 현재 해당 메소드가 사용되는 곳을 모두 알려준다.

![스크린샷 2021-07-08 오후 2 00 19](https://media.oss.navercorp.com/user/25548/files/b54c7d00-e026-11eb-8e47-2e720ff02250)

<br>

## Configuration

> Configuration이란 사용자가 앱을 쓸때, 설정으로 들어가서 ex) 언어를 영어로 바꾸거나, 글꼴을 확대시, 해상도를 키울시, 등 이때 바뀌는 것으로 **컴포넌트에서 어떤 리소스를 사용할 지 결정하는 조건이다.**
>
> ex) 화면 전환, 시스템 언어 변경, 해상도 변경, 등

```kotlin
override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
    }
}
```

***Q. `onConfigurationChanged`는 언제 불리는 걸까?!***

`Manifest`에서 액티비티의 선언시에 `android:configChanges` 를 선언할 시, `Configunration`이 바뀔 때 액티비티를 종료하고 다시 시작하는 것이 아니라 계속 실행상태로 있고, `onConfigurationChanged()` 메서드를 호출한다.

** **`screenSize` 도 넣어주어야 한다 ! ( 화면이 회전하면서 가로, 세로의 size가 바뀌기 때문)**

~~~kotlin
  <activity android:name=".MainActivity"
            android:configChanges="orientation|screenSize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
~~~

<img src="https://media.oss.navercorp.com/user/25548/files/c0071200-e026-11eb-99b4-721a9bde72e9" alt="Screenshot_1625724625" style="zoom:25%;" />

> ![스크린샷 2021-07-08 오후 3 14 50](https://media.oss.navercorp.com/user/25548/files/d01ef180-e026-11eb-961b-d83dd33af979)
>
> 
>
> 기존 화면 회전시, onPause - onStop - onDestory - onCreate - onStart - onResume으로 기존화면을 없애고 다시 그렸지만, configChanges 명시하면, onResume 상태에서 바로 onConfigurationChanged 함수만을 호출한다.

***주의***  -> onConfigunrationChange에 너무 많은 작업을 하는 것보다 새로 다시 화면을 그리도록 구현을 권장한다.

<br>

## startActivity, startActivityForResult

> 홈화면에서 앱을 클릭시 앱이 보여지는 것도 Activity의 하나이다. 
>
> Manifest 에 액티비티 선언시 `MAIN` action과 `LAUNCHER` category를 주어야 한다!

~~~kotlin
<intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
~~~

<br>

- startActivity : SecondActivity로 이동

 ~~~kotlin
    val intent = Intent(this, SecondActivity::class.java)
         startActivity(intent)
 ~~~

- startActivityForResult 

  - 액티비티 이동이 완료되었을 때 결과를 수신할때 사용하며, onActivityResult 콜백에서 Intent객체를 수신하여 데이터를 전달받을 수 있다.
  - requestCode : 해당 액티비티에서 여러번의 startActivityForResult가 호출될 수 있으므로, requestCode를 통해 onActivityResult에서 확인이 필요하다.

  

~~~kotlin
  val intent = Intent(this, SecondActivity::class.java)
        startActivityForResult(intent,1000)

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 1000) {
            // data.getData()
        }
    }
~~~

***Q. Activity 간이 데이터를 주고 받으려면?!***

intent안에 데이터들을 넣어주고, 전달하는 형식이다.

***Q. startActivity와 startActivityForResult에 차이는 ?!***

startActivity는 액티비티 이동 후 결과를 수신해오지 못하고 intent를 넘겨주면 끝이다. 하지만 startActivityForResult는 액티비티 이동 후 onActivityResult 를 통해 결과를 반환할 수 있다.

<br>

## StartActivity, StartActivityForResult 실습

### MainActivity

~~~kotlin
 findViewById<TextView>(R.id.tv_main_title).setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivityForResult(intent,1000)
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 1000) {
            Toast.makeText(this, data!!.getStringExtra("DATA"), Toast.LENGTH_SHORT).show()
        }
    }
~~~

### SecondActivity

`tvSecondTitle` `TextView`를 클릭시, `MainActivity`에서 받아온 `intent`에 DATA 정보를 넣어 `setResult`를 통해 전달해준다.

`finish()` -> 해당 `Activity`를 종료 `destory`된다.

***`getIntent()` 가 아닌 새로운 `Intent`를 생성해서 전달하는 이유는?!***

> `getIntent()`는 이전에 받아온 `Intent`를 의미하기때문에 `Intent`안의 data가 오버라이딩 되는 문제가 발생할 수 있다. 그래서 새로운  `Intent`를 생성하여 `Main`에 전달하는 것이다.

~~~kotlin
 findViewById<TextView>(R.id.tv_second_title).setOnClickListener {
            Intent(this, MainActivity::class.java).let { 
                it.putExtra("DATA","SecondData")
                setResult(RESULT_OK,it)
                finish()
            }
        }
~~~

<br>

***Q. `onActivityResult`는 언제 호출될까?***

`onStart` 전에 호출된다. 하지만 `onActivityResult`와 같은 역할을 하고있는  Android X 에서 제공하는  `registerForActivityResult` API의 콜백은 `onStart`와 `onResume`사이에 호출된다.

<br>

🔥🔥🔥 `startActivityForResult`와 `onActivityResult`가 **deprecated** 되었다!

**MainActivity**에서 `startActivityForResult`를 사용하지 않고, AndroidX에서 제공하는 `registerForActivityResult` API를 이용하여 원하는 결과 콜백을 등록할 수 있다.

`ActivityResultContract` 및 `ActivityResultCallback`을 통해 다른 액티비티 호출 후 결과를 `ActivityResultLauncher`에 반환한다.

ex) `onActivityResult` -> `StartActivityForResult()` 

~~~kotlin
 private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, result.data!!.getStringExtra("DATA"), Toast.LENGTH_SHORT).show()
        }
    }

   findViewById<TextView>(R.id.tv_main_title).setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            resultLauncher.launch(intent)

        }
~~~

~~~kotlin
private val getContent = registerForActivityResult(GetContent()) { uri : Uri? ->
	// Uri Handle
}
~~~

<br>

> 빠르게 짜는 코드가 아닌, 가독성이 좋은 코드를 짜자
>
> 누구나 알아보기 쉽도록 코드를 짜는 연습을 하자

***Q. SecondActivity로 Activity 전환시*** 

현재 액티비티의 상태는 onPause() 상태에서 SecondActivity 가 화면에 보여질때까지 기다린 다음  onStop()을 호출한다.

![스크린샷 2021-07-08 오후 5 33 55](https://media.oss.navercorp.com/user/25548/files/d01ef180-e026-11eb-91db-1209b90d1903)

<br>

### Context 란?

Activity는 각각의 Context를 가지고, ApplicationContext는 앱안에 하나만 존재하는 Context이다.

***Java에서  this를 그냥 사용하면 안되는 이유*** 

> this가 반환하는 것은 onClickListener에 익명클래스를 반환하는 것이기 때문에 에러가 발생한다. 그러므로 MainActivity.this로 어떤 this를 의미하는 것인지 명시해야한다.

<br>

## Task

### Task란?

> Activity의 작업 묶음 단위이다.
>
> ex) 라인 앱에서 사진 촬영을 전송시 , 라인 액티비티 > 카메라 액티비티로 한 Task 안에 다른 앱의 액티비티가 쌓이게 된다. 또한 한 앱안에 여러 태스크가 존재할 수도 있다.
>
> Activity는 스택이 차례대로 쌓이게 된다! (= BackStack)
>
> ​	startActivity 호출시 Activity가  Stack에 쌓이는 것

<br>

### Task 속성 부여하기

1. Android Manifest.xml의 android:launchMode 설정하기

   ​	[참고](https://developer.android.com/guide/components/activities/tasks-and-back-stack?hl=ko)

   - **standard** (기본) : acitivty의 새 인스턴스를 Stack에 쌓는다. (한  Activity의 인스턴스가 여러개일 수 있다.)

   ![스크린샷 2021-07-08 오후 6 26 34](https://media.oss.navercorp.com/user/25548/files/d0b78800-e026-11eb-9a1a-b818cae009da)

   - **singleTop** : 이미 인스턴스가  Task Stack의 맨 위에 존재한다면 기존 인스턴스를 사용한다. 

     ![스크린샷 2021-07-08 오후 6 27 22](https://media.oss.navercorp.com/user/25548/files/d0b78800-e026-11eb-87fc-dbc407d0fd9f)

     > Activity 인스턴스가 이미 Task 에 존재하면, 새 인스턴스를 생성하는 것이 아닌  onNewIntent() 메서드를 호출하여 기존 인스턴스를 사용합니다. 즉, onCreate를 호출하지않고 onNewIntent를 통해 재사용하는 것을 의미합니다.

     ![스크린샷 2021-07-08 오후 6 10 07](https://media.oss.navercorp.com/user/25548/files/d01ef180-e026-11eb-8172-3fca8cfcbfbf)

   - **singleTask** : 새 Task를 생성하고, 새 Task의 Activity를 인스턴스화 한다. 이미 Activity의 인스턴스가 이미 다른 Task에 있다면, 새 인스턴스를 생성하지 않고, 기존 인스턴스를 사용한다. (한  Activity의 인스턴스가 한번에 한개만 존재 한다.)

     ![스크린샷 2021-07-08 오후 6 27 22](https://media.oss.navercorp.com/user/25548/files/d0b78800-e026-11eb-87fc-dbc407d0fd9f)

   - **singleInstance** : singleTask 와 동일하게 Acitivity를 호출 시 새로운 Task를 생성하지만 하나의 Task에 하나의 액티비티만 존재하게 된다.

     ![스크린샷 2021-07-08 오후 6 29 23](https://media.oss.navercorp.com/user/25548/files/d1501e80-e026-11eb-95ed-a481bf21f956)



2. startActivity(Intent) 시점에 Intent Flag 설정하기

   - **FLAG_ACTIVITY_NEW_TASK** (=singleTask와 동일)

   - **FLAG_ACTIVITY_SINGLE_TOP** (=singleTop과 동일)

   - **FLAG_ACTIVITY_CLEAR_TOP**(= Task의 쌓여있는  Acitivity를 모두 날림)

     >  A->B->C에서 C->B를 띄우면, A->B가 된다. 이때 B는 onCreate부터 다시 시작한다. 이를 막기위해  FLAG_ACTIVITY_SINGLE_TOP을 같이 사용하자 

     > A->B->A->B 상황에서 맨 밑의  A를 제외한 모두를 날리고 싶어 FLAG_ACTIVITY_SINGLE_TOP 을 쓰면 A->B-> A가 된다. (맨 밑 A를 제외하고 모두 날리고 싶다면 ?! Activity-alias 를 활용하면 된다.)

   - **FLAG_ACTIVITY_CLEAR_TASK** (FLAG_ACTIVITY_NEW_TASK 와 함께 사용되어야 한다.)

     현재 Task를 모두 날리고, 새로운 Task를 새롭게 Stack의 쌓을 경우 사용

   - **FLAG_ACTIVITY_REORDER_TO_FRONT** : 스택에 이미 Activity가 있으면, 그 위 Activity를 스택의 맨 위로 올린다.

3. `<activity-alias>`  : Activity의 별명을 지정하여 사용할 수 있다. 

   ~~~kotlin
   <activity-alias
   	android:name=".SplashActivity"
   	android:targetActivity=".MainActivity"/>
   ~~~

   _ 기존 Activity를 다른 Activity로 바꿀 경우, <activity_alias>의 targetActivity만 변경해주면 된다.

   > FLAG_ACTIVITY_CLEAR_TOP에서 FLAG_ACTIVITY_SINGLE_TOP을 같이 쓰면 A->B->A가 되는데 , A만 남기고 다 지우고싶다면  Activity_alias를 사용하자.

<br>



### Task 실습 

>  B Activity 의 launchMode를 singleTop 으로 한 상태에서, B 에서 자신 B를 FLAG_ACTIVITY_CLEAR_TASK 로 실행한다면 어떻게 될까? 

manifest에서 선언한  singleTop에 의해 새롭게 Task를 생성하는 것이 아닌 그냥  기존의 B Activity를 사용한다.

> B Activity가 singleTask 인 상태에서 A -> B -> C Dialog Activity를 띄우면 밑에 쌓여서 눈에 보이는 Activity는 무슨 Activity 일까? 

A Activity가 뜨게 된다. 

<br>

## TaskAffinity

Activity 별로 TaskAffinity를 가지고 있고,  launchMode의 속성에 따라 동작 방식이 다르다.

하지만 일반적으로  같은  TaskAffinity를 갖게되면, 같은 Task에 들어가게된다.

(설정 X -> 패키지명이 default Task이다.)











## 