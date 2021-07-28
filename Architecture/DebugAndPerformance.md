# Android Debugging

** 디버깅 : 오류나 예기치 않는 행동을 수정하는 것

## Logcat

- Log를 추가하여 Logcat으로 확인할 수 있다!

- Stacktrace를 확인하여 로그들을 볼 수 있다

<br>

## Debugger

- Run -> Debug 'app'

- 디버깅이 가능한 Build Variant 실행하기

  ~~~
  android {
  	buildTypes {
  		customDebugType {
  			debuggable true
  		}
  	}
  }
  ~~~

  ** 기본 debug는 이미 debuggable true 임!

## Show Layout Bounds

개발자 옵션에 Settings > Developter options > Show Layout Bounds

- View의 Margin , Padding 등 레이아웃의 범위를 대략적으로 확인할 수 있다!

더 자세히 확인하려면 

**Tools > Layout Inspector**를 이용하자!

<br>

# Performance

## Profiler

1. Android 프로파일러란? 현재 프로파일링 중인 프로세스와 기기를 표시해준다.

2. Session 창에서 확인할 세션을 선택하거나 새 프로파일링 세션을 시작할 수 있다.

3. 확대/축소 버튼을 사용하여 얼마나 많은 타임라인을 확인할 지 관리하거나, Attach to live 버튼을 사용하여 실시간 업데이트로 건너뛸 수 있다.

4. 이벤트 타임라인에는 키보드 활동, 볼륨 조정 변경사항, 화면 회전 등 사용자 입력과 관련된 이벤트가 표시된다.

5. CPU, 메모리, 네트워크 및 에너지 사용량의 그래프가 포함된 공유 타임라인 보기도 존재한다.

![스크린샷 2021-07-12 오후 3.57.03](../image/profiler.png)

### Network (+Library)

> OkHttp 를 사용하는 경우 Steho 등을 이용하여 Network Response 확인이 가능하다.

https://github.com/facebook/stetho

## Profile HWUI rendering

GPU Rendering 속도가 16ms 를 초과하는 지 확인할 수 있는 방법!

Settings > Developer options > Profile HWUI Rendering

> 가로 녹색선이 16ms 를 의미한다!



## GPU overdraw

View의 Overdraw 상태를 확인할 수 있는 방법

Settings > Developer options > Debud GPU overdraw

** Overdraw란!?

한 프레임 내의 동일한 픽셀을 여러번 그리는 것을 의미한다.

불필요한 오버드로는 제거하는 것이 좋다..!

![스크린샷 2021-07-12 오후 3.57.56](../image/overdraw.png)

### Overdraw 줄이는 방법

1. 레이아웃에서 불필요한 배경 제거
2. 뷰 계층 구조 평면화
3. 투명도 감소



#### background 배경을 바꾸려면 

theme에 windowBackground를 변경하자!!



#### Glide 

사용시 placeholder() 를 제공하여 이미지 로딩 중 기본 이미지를 설정을 제공해준다.

