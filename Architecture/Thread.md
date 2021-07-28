# Thread

## MultiThreading이란?

스레드란 ? 프로세스 안에 존재하는 하나의 작업 흐름 (단위)를 의미한다.

멀티 스레드란 ? 프로세스 안에 스레드가 하나가 아닌 여러개 스레드가 존재하는 것을 의미하며, 한 프로세스에서 여러개의 스레드가 동시에 수행하는 것을 말한다.



#### 멀티 프로세스보다 멀티 스레드를 사용하는 이유는 무엇일까?

> 멀티 프로세스를 이용시, 프로세스를 생성하는 데 스레드를 생성하는 것보다 비용이 더 많이 든다.
>
> 또한, 각각의 독립적인 영역을 가지고 있으므로 프로세스간의 통신이 어렵다. (스레드에 비해)
>
> 멀티 스레드는 한 프로세스에 여러개 스레드들이 프로세스 내의 자원을 공유하고 ContextSwitching 비용이 더 적다.
>
> 하지만 공유 자원에 대한 동기화 문제, 데드락을 주의해야한다!



#### 멀티 스레딩 개념

- 하나의 프로세스에 여러개 스레드를 실행하여 많은 작업을 동시에 처리한다.
- 멀티 프로세싱과 멀티 태스킹과는 다른 개념
  - 멀티 프로세싱 :   CPU N 프로세스에 프로세스 N개를 수행하는 것
  - 멀티 태스킹 : 다수의 Task 존재하는 것
- Thread는 해당 프로세스의 여러 자원(코드, 데이터) 등을 공유할 수 있다!!
  - Code, Data, Heap 메모리 영역은 프로세스 내 다른 스레드와 공유하고 Stack 영역만 독립적으로 가지고 있다

- 안드로이드에서 시간이 걸리는 작업을 처리하면서 UI도 계속 반응해야하므로 멀티 스레딩은 필수적이다..!

<br>

#### 자바에서 Multithreading

- Java는 Thread , Runnable 등 요소를 통해 멀티 스레딩을 지원한다
- Android 에서도 Java의 멀티스레딩 도구를 활용할 수 있다!

<br>

## Thread & Runnable

> 작업을 Runnable로 만든 뒤, thread에 넣어 해당 작업을 수행하도록 구현한다.

~~~kotlin
fun onButtonClick() {
	val result = plus1(100)// 오래걸리는 작업
	println("result : $result")
}

fun plus1(input : Int) : Int {
	try {
		Thread.sleep(1000)
	} catch(e : InterruptedException) {
	
	}
	return input +1
}

~~~

~~~kotlin
val runnable = Runnable {
            this.run {
                val result = plus1(100)
                Log.d("MainActivity","result : $result")
            }
        }
val thread = Thread(runnable)
thread.start()
~~~

- Thread : 하나의 스레드를 나타내는 클래스
  - 스레드 자체에 대한 동작 관리 (시작 / 중지 / 대기)
- Runnable : 하나의 작업을 나타내는 클래스

<br>

## Multithreading 이슈

병렬 처리 이슈

- 여러 스레드가 동시에 실행할 경우, **실행 순서가 보장되지 않는다..!**

- 하나의 데이터 동시에 접근시 실행 순서에 따라 결과가 바뀔 수 있다.

  *Why?* 서로 다른 공간의 변수에 접근하거나 변경할 수 있기 때문..!

  **값을 읽고 변경하고 다시 쓰는 동작이 Thread safe 하게 발생하지 않는다.**

> 어떤 변수는 메인 메모리에서 읽어와 캐시에 저장하고 CPU가 그걸 꺼내서 사용하는데 , 
>
> **쓰고 읽는 동작이 한번에 일어나지 않는다..!**
>
> 다른 스레드에서는 잘못된 값을 읽어갈 수 있다!! (동시에 접근하는 경우)

## Race Condition

하나의 자원을 가지고 여러 스레드가 경쟁하는 상태

동시에 하나의 자원을 접근하려면 RaceCondition이 발생할 수 있다!

-> Race Condition 관리가 필요하다!

### Synchronized 제어자 사용

> synchronized 제어자를 붙이면 **하나의 자원에 하나의 스레드만** 접근 가능해진다.

### Lock 객체 사용

> lock은 Synchronized와 유사하나, 자원을 사용하는 시작과 끝을 명시적으로 작성할 수 있다!

### AtomicInteger 사용

> 해당 변수에 대한 연산을 Atomic 하게 (읽고 쓰는 동작을 한번에 하도록) 만들어 주는 것
>
> 자원을 사용하는 중에는 다른 스레드가 접근할 수 없도록 막아준다.

** *Synchronized나 Lock은 block 형태로 자원 접근을 제한하는 것이므로 AtomicInteger를 사용하는게 더좋다..!*

Atomic Integer는 **volatile**로 값을 저장한다.

> Volatile - 값들을 메인 메모리에 저장하겠다는 의미
>
> 한쪽에서만 변경을 하고, 나머지는 읽는 과정이라면 Volatile로 해결할 수 있다!



---

코드 블록

- 암시적 : Synchronized
- 명시적 : Lock 객체

원시값

- Atomic

## Executor 프레임 워크

> Java 에서 제공하는 고수준 비동기 작업 실행 프레임 워크
>
> Thread : 저수준 API

작업의 등록과 실행을 분리하고 '실행 정책'을 추상화 한다!

- 어느 스레드?
- 어떤 순서?
- 동시에 몇개?
- 최대 몇개를 대기?
- 작업을 거절해야 할 경우 어떤 작업을?
- 작업의 실행 전후에 어떤 동작을?

### Executor Interface

어떤 작업(Runnable)을 실행하는 기능을 나타내는 인터페이스

Why? Executor 왜 쓰는 걸까?!

Thread Pool 을 얻기 위해 !!

<br>

### Thread Pool

- 작업의 실행하는 스레드를 '풀'로 관리
- 여러 스레드 작업을 분배해야되므로 **동기화 큐**가 필요하다.
- 미리 작업에 대한 스레드를 생성해놓고, 스레드가 필요한 프로세스에게 제공한다.
- 이미 생성된 스레드를 재사용하므로, 시스템 자원, 작업 시작 딜레이가 줄어든다.
- 생성되는 스레드 개수를 통제하고, CPU 코어를 최대한 활용할 수 있으며 과**도하게 많은 스레드가 시스템 자원을 두고 경쟁하는 상황을 방지한다**
- **Worker Thread에서 관리된다.**

### ExecutorService 🔥🔥🔥

- **스레드 풀에 대한 생명주기를 관리하는 Executor**
- 큐에 남은 작업을 정리하거나, 스레드를 종료시키는 등 메커니즘을 제공
- Executors 클래스의 팩토리 메소드를 통해 미리 정의된 스레드 풀을 사용하는 ExecutorService를 생성한다.

Executors.**newFixedThreadPool** : 최대 크기가 고정된 스레드 플

Executors.**newCachedThreadPool** : 크기가 유동적인 스레드 풀

Executors.**newSingleThreadExecutor** : 단일 스레드에서 동작하는 스레드 풀

<br>

## 안드로이드의 Thread

안드로이드는 MainThread, Worker Thread 두가지로 나눠진다.

### Main Thread

- 안드로이드 시스템에서 특수하게 취급하는 가장 중요한 스레드
- **앱의 실행과 함께 생성**
- 가장 높은 우선권
- **앱의 핵심 메서드는 대부분 메인 스레드에서 실행된다!**
  - 액티비티 라이프사이클 콜백
  - 뷰의 이벤트 리스너
- 메인 스레드가 5초 이상 반응하지 않으면 **ANR** 발생한다!

#### Main Thread에서 할 수 있는 일!

- UI 를 다루는 일
- 다른 스레드에서는 할 수 없다 (Exception 발생)

#### Main Thread에서 해선 안되는 일!

- 네트워크 I/O (Exception 발생)
- 파일 I/O
  - DB 조회 / 생성 / 수정 / 삭제
  - SharedPreference
- 그 외 오래걸리는 모든 작업
  - JSON 파싱
  - 비트맵 디코딩

## Looper & Handler

### 백그라운드 작업 완료 후 UI 업데이트 하는 방법

- *`runOnUiThread()`* - 액티비티에서 제공하는 백그라운드 작업을 하고 UI를 업데이트 하도록 하는 Thread

  runOnUiThread 안의 작업은 메인 스레드에서 실행하게 된다.

- `View.post()`  - post를 통해 작업이 완료되면 UI 에 변경될 수 있도록 구현할 수도 있다.

#### 메인 스레드에게 Runnable 을 실행하라 메시지를 전달하는데, 이때 메인 스레드는 어떻게 메시지를 받아 처리할 수 있을까?!

- Looper : 메인스레드에게 Runnable 작업이 오는지 안오는지 확인하고 있는 반복자
- Handler : 메인 스레드와 백그라운드 스레드는 다른 공간이기 때문에 이때 Handler를 통해 메인 스레드의 존재하는  Message Queue 에 UI 스레드 작업을 전달한다.

- Message Queue : Handler 로부터 전달받는 메시지객체나 Runnable 작업을 큐에 저장하여 하나씩 꺼네어 Looper에게 작업을 제공한다.

** Runnable은 메시지 객체로 래핑된다.

** 래핑된 메시지는 핸들러를 통해 메인 스레드의 메시지 큐에 삽입되고, 메인 스레드는 루퍼를 통해 **메시지 큐**에서 메시지를 읽어들여 처리한다.

**Thread : Looper : MessageQueue = 1 : 1 : 1**

** 핸들러는 별도이다. 

## Message

- 데이터나 작업을 담는 객체
- Message Object Pool 이 존재한다.
  - Message.obtain()을 통해서 생성
  - Message 객체를 매번 생성할 수도 있지만, 재사용이 불가능하기 때문에 Object Pool을 제공한다.

## Message Queue

> 핸들러와 루퍼 사이에 있는 것

- 메시지들의 Single Linked List로 메시지가 처리되어야하는 순서대로 연결한다.
- 응용 프로그램에서 직접 사용할 일 거의없다!

## Looper

- 메시지 큐로부터 메시지를 하나씩 읽어들여 처리함

- 무한 루프이므로 **루퍼가 동작하는 스레드는 루퍼를 돌리는 것 외에 다른일이 불가능하다**

** *루퍼는 어떻게 하나씩 읽어들일까?!*

- 무한 루프를 돌며 한 루프에서 하나씩 메시지를 가져온다.
- 메시지 큐가 텅 비면 스레드는 sleep 한다.

<br>

- Looper.prepare() : 현재 스레드에 대한 Looper 생성
  - 호출 X null
- Looper.loop() : 루퍼가 동작하면서 무한 루프에 빠진다.
  - Looper 객체를 통해 시작하는 것은 아니다 
- Looper.getMainLooper() : 항상 존재함!

## Handler

-  메시지를 보내는 쪽 , 받는 쪽을 위한 기능 제공
  - 보내는 쪽 : 메시지 큐에 삽입
  - 받는 쪽 : 메시지에 대한 콜백 호출

** 한 핸들러를 통한 메시지는 그 핸들러가 작업을 모두 해야한다.

- **핸들러를 통해 보낸 메시지, 그 핸들러만 받을 수 있다!**
  - ***보내는 쪽, 받는 쪽 핸들러 객체를 공유해야한다.***

### Handler 생성자와 Looper

- 받는 쪽
  - 현재 스레드는 반드시 **Looper.prepare() 된 상태여야 한다.**
  - **이 Handler는 메인 스레드에서 생성되어야 정상 동작이 가능하다**

#### Runnable(작업) 객체를 보낼때

- post

#### 데이터를 보낼때

- sendMessage

#### 빈 데이터 보낼때

- sendEmptyMessage

### Handler 주의 개념

> 내가 원하는 작업이 가장 먼저 실행되도록 보장할 수는 없다..!
>
> 다른 Handler가 또 atFrontOfQueue를 호출할 수 있기 때문

####  전송 취소

- removeCallbacks
- removeMessages
- removeCallbacksAndMessages

***전송 취소가 필요한 상황***

Ex) 사용자가 5초이상 작업하지 않을 때 delay를 이용하여 자동으로 다음 화면으로 이동할 수있도록 한다.

다음 화면 이동 작업을 5000ms 지연하여 postDelayed()를 사용하자

화면 터치시 이동 작업을 removeCallbacks()로 제거하자.

### Handler의 메시지 처리 방법

1. 생성자에 Handler.Callback 지정하는 방법
2. Handler의 handlerMessage()를 오버라이드 하여 구현

-> 콜백이  true 리턴시 handlerMessage()는 호출되지 않는다.

메시지에 Runnable이 있는 경우 어느 handlerMessage()도 호출되지 않는다! (작업을 바로 수행해버림)

<br>

## HandlerThread

### 백그라운드 스레드가 메시지를 받으려면?

- 핸들러가 백그라운드 스레드의 looper와 연결되어야 한다.

- 백그라운드 스레드에 looper를 준비해주고, handler를 만들어 그 핸들러에 루퍼를 지정한다.

** 주의 ⚠️  prepare 된 후에 루퍼가 생기는 것이므로 순서를 유의하자!

~~~kotlin
Looper.prepare()

val looper = Looper.myLopper()
~~~

### HandlerThread란?

루퍼를 준비하고 시작하는 과정을 미리 구현해 둔 스레드

LooperThread 가 더 명확하다!! (Handler Thread X)

Handler를 직접 가지고 있는 것이 아닌, Handler가 Thread 외부에 있는 전형적인 Thread이다. 

일반적으로  **UI와 관련 없으나 단일 스레드에서 순차적 작업이 필요한 경우 사용한다.**

<br>

## AsyncTask

> 스레드나 메시지 루프 등의 작동 원리를 몰라도 하나의 클래스에서 UI 작업과, backgroud 작업을 쉽게 할 수 있도록 안드로이드에서 제공하는 클래스

- 캡슐화가 잘되어있어 코드 가독성이 증가

- 작업 스케쥴을 관리할 수 있는 콜백 메서드를 제공하고, 필요시 쉽게 UI 갱신도 가능하며 작업 취소가 쉽다.

→ 리스트에 보여주기 위한 데이터 다운로드, 등 UI 와 관련된 독립적 작업 실행시 AsyncTask 로 간단히 구현할수 있다.

### AsyncTask is Deprecated

단점 - 재사용이 불가능, 종료하지 않으면 종료가 되지 않는다.

1. **메모리 누수** 문제로 deprecated되었다.

2. **순차 실행으로 인한 속도가 저하된다.**

3. fragment Async 실행 문제

   > 프레그먼트에서 Async 를 실행후 back key로 액티비티를 종료하면 프래그먼트의 액티비티와 분리되면서 getContext, getActivity가 null을 리턴하여 **postExecute에서 널포인트가 발생한다.**

4. 예외처리 메서드가 없음
5. 안드로이드 버전이 올라가면서 기본 동작이 순차 실행으로 바뀌면서 **AsyncTask는 병렬 실행시, 순차 실행을 보장하지않는다!**

