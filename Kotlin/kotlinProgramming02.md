# 변수와 자료형, 연산자

## 01 . 코틀린의 프로젝트, 모듈 패키지, 파일 관계

- 프로젝트
- 모듈
- 패키지
- 파일(클래스)

프로젝트 > 모듈 > 패키지 > 파일 (클래스)

***패키지를 만들어야 하는 이유?***

같은 이름의 파일(클래스)를 만들었을 때 패키지가 다르면 오류가 발생하지 않는다!

<br>

### 코틀린의 기본 패키지

- `kotlin.*` : Any, Int, Double 등 핵심 함수와 자료형
- `kotlin.text.*` : Text와 관련된 API
- `kotlin.sequences.*` : 컬렉션 자료형의 하나로 반복이 허용되는 개체
- `kotlin.ranges.*` : if, for문의 사용할 범위 관련 요소
- `kotlin.io.*` : 입출력 관련 API
- `kotlin.collections.*` : List, Set, Map 자료구조 컬렉션
- `kotlin.annotation.*` : 애노테이션 API

<br>

## 02. 변수와 자료형

```kotlin
val username : String = "Kildong"
```

`val` - 선언 키워드

`username` - 변수 이름

`String` - 자료형

`"Kildong"` - 값

** 주의

```kotlin
var username // 자료형을 지정하지 않은 변수는 사용 XXX
```

<br>

### 변수 선언법

변수이름은 숫자로 시작하면 안된다. 코틀린 키워드는 사용 불가능 의미있는 단어를 사용해서 만드는 것이 좋다 여러 단어로 사용시 `카멜 표기법`

** `카멜 표기법`

여러 단어로 된 변수 이름 지정 **첫번째 글자는 소문자**, 나머지 각 단어의 **첫번째 글자는 대문자**로

ex ) `numberOfBooks`

**변수**는 첫번째 글자 소문자로, **클래스 인터페이스**는 대문자로

<br>

## 03 코틀린 자료형

> 코틀린의 자료형은 참조형 자료형을 사요한다.

- **기본형** (Primitve Data Type)

  가공되지 않은 순수한 자료형

- **참조형** (Reference Type)

  객체를 생성하고 동적 메모리 영역(Heap)에 데이터를 저장 후, 참조하는 자료형

Java

- 기본형 : int, long, float, double
- 참조형 : String, Date

Kotlin

- 참조형만 사용

→ 참조형으로 선언한 변수는 성능 최적화를 위해 코틀린 컴파일러에서 기본형으로 대체된다.

<br>

### 부호가 있는 정수 자료형

| 자료형 | 크기          | 값의 범위                 |
| ------ | ------------- | ------------------------- |
| Long   | 8byte (64bit) | -2^63 ~ 2^63-1            |
| Int    | 4byte (32bit) | -2^31 ~ 2^31-1            |
| Short  | 2byte (16bit) | -2^15 ~ 2^15-1            |
| Byte   | 1byte (8bit)  | -2^7 ~ 2^7 -1 (-128 ~127) |

```kotlin
val exp01 = 123 // Int
val exp02 = 123L // Long
val exp03 = 0x0f // 16진수 Int
val exp04 = 0b00001011 // 2진수 Int
```

*** 명시적으로 Short 선언하지 않으면 Int형으로 추론됨*

<br>

### 부호가 없는 정수 자료형

| 자료형      | 크기          | 값의 범위   |
| ----------- | ------------- | ----------- |
| ULong       | 8byte (64bit) | 0 ~ 2^64 -1 |
| IntUInt     | 4byte (32bit) | 0 ~ 2^32 -1 |
| ShortUShort | 2byte (16bit) | 0 ~ 2^16 -1 |
| ByteUByte   | 1byte (8bit)  | 0 ~ 2^8 -1  |

```kotlin
val uint : UInt = 153u
val ushort : UShort = 65535U
val ulong : ULong = 46232334uL
val ubyte : UByte = 255u
```

<br>

### 실수 자료형

| 자료형 | 크기         |      |
| ------ | ------------ | ---- |
| Double | 8byte(64bit) |      |
| Float  | 4byte(32bit) |      |

```kotlin
val exp01 = 3.14 // Double
val exp02 = 3.14F // float
```

<br>

### 논리 자료형, 문자 자료형

| 자료형  | 크기         |      |
| ------- | ------------ | ---- |
| Boolean | 1bit         |      |
| Char    | 2byte(16bit) |      |

<br>

### 문자열 자료형 저장방식

문자열 자료형은 스택에 주소값을 가지고 **힙**영역 String Pool에서 해당 값을 참조하도록 구현되어있다.

** `${''}` 를 이용하여 큰따옴표, $를 표현할 수 있다.

`"""` -다중 문자열 시 사용

** `typealias Username = String` 을 이용하여 자료형에 별명을 붙일 수 있다.

<br>

## 04 null 변수 처리

null 인 변수에 접근하면 NPE 가 발생한다.

** null 을 할당허용하려면 '`?`' 를 자료형 뒤에 명시해야한다!

** `세이프 콜`

```kotlin
println("str1 $str1 length : ${str1?.length}") .. str1을 세이프 콜로 안전하게 호출한다.
```

→ str1을 검사한 다음, null이 아니면 str1의 멤버변수인 length에 접근하여 값을 읽어온다.

<br>

** **non- null** - '`!!`'

non - null 단정 기호는 변수에 할당된 값이 null이 아님을 단정하므로 → **컴파일러가 null 검사 없이 무시하게 된다.**

**→ NPE 발생 할 수 있음**

<br>

### 세이프 콜과 엘비스 연산자로 null 허용 변수 안전하게 사용하기

```kotlin
fun main() {
	var str1 : String? = "Hello Kotlin"
	st1 = null
	println("str1 : $str1 length : ${str1?.length ?: -1}") // str1이 null이면 -1 리턴
}
str1?.length ?: -1

if(str1 != null) str.length else -1
```

<br>

### 자료형변환

```kotlin
toByte()
toDouble()
toInt()
toFloat()
toChar()
toShort()
toLong()
```

<br>

### 값 비교와 참조 비교

- ==

  값만 비교

- ===

  참조 주소 비교

```kotlin
val a : Int = 128
val b : Int? = 128

println(a==b) // true
println(a===b) // false
```

→ Int형은 기본형으로 변환되어 스택에 저장, Int? 형은 참조형에 저장 즉, 힙의 참조 주소가 저장된다.

<br>

### 스마트 캐스트

#### 자동 형변환

- **`Any` 형**

  코틀린의 최상위 기본 클래스로 어떤 자료형이라도 될 수 있는 특수한 자료형

```kotlin
val x : Any
x = "Hello"

if (x is String) print(x.length) 
```

→ `is` 변수의 자료형을 검사한 다음 그 변수를 해당 자료형으로 변환함

```kotlin
val x : String = y as String

val x : String? = y as? String
```

→ `as` 형변환이 가능하지 않으면 예외를 발생시킨다. // y가 null이면 예외 발생

→ `?` 를 이용하여 null 가능성 고려하여 예외 발생 피할 수 있다!

<br>

#### 묵시적 변환

Any 형은 자료형이 특별히 정해지지 않은 경우 사용할 수 있다!

→ Any : 모든 클래스의 슈퍼 클래스