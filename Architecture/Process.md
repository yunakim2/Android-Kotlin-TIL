# Android Process

## Process란?

Android Application 대부분의 process의 경우 자신의 소유 리눅스 프로세스 위에서 동작한다.

***Application 스스로가 process의 생명주기를 직접 제어할 수 없다!***

> 몇가지의 기준을 가지고 메모리를 회수하여 프로세스를 종료한다.

** BroadcastReceiver.onReceiver() 내에서 thread를 시작시키고 return 하는 경우, 프로세스 종료 버그가 흔하게 발생한다!

<br>

## Process 우선순위

### Foreground Process

- Activity가 스크린 최상단에 표시되어 유저와 상호작용하는 경우

  > onResume() 이 호출된 이후

- BroadcastReceiver가 동작하고 있는 경우

  > onReceiver() 가 실행되고 있을 때

- Service가 callback 메소드를 실행하고 있는 경우

  > onCreate(), onStart(), onDestory()

-> 시스템에 Foreground Process는 많지 않으며, 메모리가 매우 부족하여 해당 프로세스를 실행하지 못하는 경우를 제외하고는 종료되지 않는다!!

### Visible Process

- 사용자에게 보여지고 있는 Activity를 실행중인 프로세스

  > onPause()이후 Dialog 등에 의해 화면 일부가 가려진 상태

- Service.startForeground() 메소드로 인해 시작된 서비스를 갖고 있는 프로세스

  > 사용자에게 인식되고 있는 상태

- 사용자가 인식하고 있는 시스템의 일부 서비스를 호스팅하고 있는 프로세스

  > Wallpaper, input method service 등

-> Foreground Process 보다 덜 제한적이므로 더 많을 수는 있지만 시스템에 의해 종료될 수 있다. 다만 시스템은 매우 중요한 프로세스라고 인식하여 가능한 동작할 수있게 한다.

### Service Process

- Service.startService() 에 의해 시작된 Service를 갖는 프로세스

  > 사용자에게 직접 보여지지는 않지만 사용자가 지속적으로 관심을 갖을 만한 작업이 동작중인 상태
  >
  > (다운로드나 업로드 등)

- 장시간 실행시 cached process 중요도로 강등이 가능하다

-> 시스템은  visible process를 동작시킬 만한 메모리가 충분할 시 계속 동작할 수 있도록 유지한다.

### Cached Process

- 현재 사용되지 않는 프로세스, 언제든 종료 가능

- LRU 리스트에 유지되며 리스트의 마지막 프로세스가 가장 먼저 종료된다.

  정상적인 시스템은 여러 Cached Process를 애플리케이션 간의 효율적 전환을 위해 항상 갖고있고 사용 가능하다!

-> LRU 리스트의 순서에 대한 정확한 규칙은 플랫폼 구현 상세에 해당, 다만 일반적으로 사용자에게 더 유용한 프로세스를 다른 프로세스 보다 유지하기 위해 노력하며 프로세스 수에 대한 제한, Cached 에 머물러 있는 프로세스 시간 등이 규칙에 추가로 적용된다!

## Low Memory Killer (LMK)

- 시스템을 위한 메모리 확보가 여러번 실패시 onTrimMemory를 호출한다.
- 이후 지속적으로 부족시 LMK가 사용되며 LMK는 OOM 점수로 동작중인 프로세스 중 먼저 종료할 프로세스를 선정한다.

#### 프로세스 우선순위 (숫자 높을 수록 높음)

1. 실행된 적 있으나 지금 활성화 되지 않음
2. 가장 최근 사용한 백그라운드 앱
3. 런처 앱 종료시 배경화면 사라지는 경우
4. 서비스가 동작하고 있는 앱 (동기화/업로드/다운로드 등)
5. 현재 사용중인 앱
6. 전화/wifi 등 핵심 서비스
7. 시스템
8. 커널 스왑 데몬 등과 같이 저수준 네이티브 프로세스

## MultiProcess

> 대부분 단일 프로세스에서 동작하나 특정 상황의 경우 하나의 앱이 멀티 프로세스를 갖는 경우도 존재한다.
>
> 특정 컴포넌트를 다른 프로세스에서 동작하게 구현해야하는 상황

#### android:process

Component 별 process를 할당할 수 있다. Manifest에 해당 Component에 android:process를 통해 명시하면 된다.

## IPC

> 프로세스간의 통신을 의미
>
> ex) 소켓, 파일 , 공유 메모리 , 파이프라인 ,등

### Binder

> 안드로이드 커널에 탑재된 **Binder Driver**를 통해 프로세스 통신을 한다.

ex) Context.getSystemService() 이용하는 것 역시 Binder를 통해 시스템과 통신하는 것이다

### BroadcastReceiver / Messenger / AIDL

> 브로드캐스트 리시버를 통해 프로세스 간의 통신이 가능하다 (Intent를 주고받음)
>
> Messenger도 가능하다,  AIDL + Binder를 이용하면 RPC도 가능하다!



