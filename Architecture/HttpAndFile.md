# Http / File 다루기

## Http 통신

> 소켓 등 자바에서 제공하는 네트워크 통신 수단을 안드로이드에서 이용 가능하다
>
> http를 주로 이용하여 서버와 데이터를 주고 받는다.

- Manifest에 internet 퍼미션이 등록되어있어야 한다!

<br>

### http 통신과 멀티 스레딩

- 통신 작업은 메인스레드에서 처리할 수 없다!

- 반드시 백그라운드 스레드에서 처리해야한다.

- 응답을 파싱하는 작업도 백그라운드에서 처리하자

  - 앱에서 바로 사용할 수 있는 모델 객체를 생성하여 메인 스레드로 전달하자

    주의 ⚠️  복잡한 파싱 작업을 메인 스레드에서 처리하지말자~

> HTTP 요청, 응답 파싱은 백그라운드 스레드에서 처리하자
>
> 그 결과를 메인 스레드로 전달하여 화면에 반영한다!

<br>

### URL과 Uri

- Java.net.URL (Uniform Resource Locator)
  - 자바 제공
  - 네트워크 동작시 사용한다.
- Android.net.Uri(Uniform Resource Identifier) - 식별자
  - 안드로이드 제공
  - Intent나 Bundle등으로 데이터 전달시 사용한다.

** 모든 URL은 Uri 지만 만  Uri는 URL이 아니다

>  Uri > URL

### HttpURLConnection

자바에서 제공하는 Http 통신 클래스로, 안드로이드에서도 HttpURLConnection을 가장 기본적으로 사용한다.

생성

- URL.openConnection() 팩토리 메서드로 객체를 생성
- 실제 네트워크 연결이 일어나지는 않는다!

요청 속성 지정

- HTTP 메서드 (GET, POST) : setRequestMethod()
- 헤더 : setRequestProperty() // 설정값
- 바디 : getOutputStream() // 실제 데이터 값들

연결

- connect()
- **연결은 동기적으로 일어나며, 즉 응답이 올 때까지 스레드가 블록된다!**
- connect() 를 명시적으로 호출하지 않더라도 아래 나열된 응답 메서드를 호출하면 자동으로 연결이 일어난다..!

응답

- 응답 코드 : getResponseCode()
- 응답 상태 메시지 : getResponseMessage()
- 응답 내용 : getInputStream()

*** 실제 연결은 connection.getInputStream()에서 이뤄진다.*

<br>

### Json 응답 파싱

- Java 객체로 파싱해야 한다.
- JsonReader를 사용하는 방법
  - Depth first Search
  - 요소 하나하나를 순서대로 읽어와 처리한다.
  - Array []
  - Object {}
  - skipValue로 skip 가능하다
- JSONTokenizer를 사용하는 방법
  - 전체 스트림을 모두 읽어와 문자열 -> 객체 순으로 반환

<br>

### Gson

json 구조의 직렬화된 데이터를 JAVA 객체로 역직렬화, 직렬화 해주는 자바 라이브러리

https://pluu.github.io/

## File 다루기

보안을 위해 파일 저장소를 앱별로 엄격하게 구분하고 있다!

- 앱 전용 저장소

  - 내부 
    - 데이터
    - 캐시

  - 외부(SD카드)
    - 데이터
    - 캐시

- 공용 저장소

  - 외부(SD카드)

### 데이터 / 캐시

- 데이터

  - 사용자 정보, 설정 등 

  - 지속적 유지 데이터

- 캐시

  - 말그대로 캐시
  - 임시 데이터로 언제든 삭제 될 수 있음

- 데이터 캐시 모두 앱 삭제시 자동으로 삭제됨

  > android 6.0 부터 allowBackup flag로 설정을 유지할 수도 있다!
  >
  > 공용 외부 저장소에 저장한 파일은 자동으로 삭제되지 않는다!

### 앱 전용 저장소 접근하기

Context 객체를 통해 접근한다.

File 객체가 반환된다.

#### 내부

- context.getFilesDir() : 앱 전용 내부 저장소 최상위 디렉터리
- context.getDataDir() : 앱 전용 내부 데이터 저장소
- context.getCacheDir() : 앱 전용 내부 캐시 저장소

#### 외부

- context.getExternalFilesDirs() : 앱 전용 외부 데이터 저장소
- context.getExternalCacheDirs() : 앱 전용 외부 데이터 캐시 저장소

### 앱 외부 공용 저장소

> **공용 저장소는 퍼미션이 필요하며** (READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
>
> 앱 전용 저장소는 필요하지 않다

### Shared Preference

간단한 키 - 값 저장소로

환경 설정 등 간단한 데이터를 저장/공유할 때 유용하다

**실제 데이터는 xml 형식으로 앱 전용 데이터 저장소에 파일로 저장된다**

Context.getSharedPreferences() 를 통해 생성한다.

## Database

> 네트워크가 없는 환경에서 사용자에게 좋은 경험을 제공하기 위해 앱 내에 데이터를 잘 저장하는 것이 필수적이다..!

### SQLite

안드로이드 기본 내장

모바일 앱 환경 적합 관계형 DBMS

***데이터 베이스 당 하나의 파일이 생성되어 백업 / 복구가 쉽다***

#### - SQLiteOpenHelper

- 생성자 
  - context , 디비 이름, 커서 팩토리, 버전을 파라미터로 받는다
  - db 의 이름과 버전은 하위클래스로 지정하자 (외부 노출 안되도록)
- onCreate()
  - DB자체가 없을 경우 호출되며, 테이블 생성, 초기 데이터 삽입 등을 처리한다!

#### - SQLiteDatabase object

데이터 객체 읽어올 때 getWritableDatabase()로 DB를 얻어와 replace(), update() 호출한다

- 실질적 CRUD 메서드를 호출하는 객체

- onCreat, onUpgrade는 파라미터로 전달된다

- ContextValues

  - 이름 - 값의 쌍
  - 데이터 삽입이나 업데이트 시 사용
  - put()

- Cursor

  - 데이터베이스 쿼리 결과의 random read-write access 제공

  - getCount(), moveToFirst(), moveToNext()

#### - DataBase 업그레이드

데이터 스키마 변경시 업그레이드 필요..! 버전을 증가시키고 onUpgrade()를 구현하자

*onUpgrade()는 현재 사용자의 DB버전을 파라미터로 넘기므로 버전에 따라 업데이트 쿼리를 실행하도록 해야한다*

<br>

### Database 기능을 쉽게 제공하는 라이브러리

- ROOM (Jatpack 라이브러리 중 하나로 안드로이드 내부 DB 구현시 사용)
- Realm
- Lynk

