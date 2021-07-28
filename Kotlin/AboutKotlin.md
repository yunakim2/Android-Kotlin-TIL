# About Kotlin

Kotlin = Java 코드와 연동되고, Java 보다 더 나은 언어를 만들자 

## 코틀린 특징

- Type Inference 

- Null Safety

- **Property**

- Code Block & Return

- Operator Overloading

- Function

  - Currying
  - Function Reference
  - Method Extension

- Simplified Condition

- Implicit Return Value

- Concise Class (간결화 된 클래스)

- No static : 전역변수, 함수 없다

- No new : 객체 생성시 new를 쓰지 않고, `()` 를 이용하여 객체를 생성한다.

- **No Primitive Wrapper Class** :

  Java에서는 `Primitive` 타입과 `Wrapper` 클래스가 존재하여 `Primitive` 타입을`Wrapper` 클래스를 통해 `Wrapper` 타입으로 변한하지만, Kotlin 에서는 `Primitive` 타입들이 모두`Wrapper` 타입으로 사용하고, **`Reference` 타입인 참조형만 사용한다**. 

  ** `Wrapper` 타입 : Int, Float, Long, Double *(primitive 타입을 객체화 한 것)*

  ** `Primitive` 타입 : int, float, long, double

  ***Kotlin의 Primitive 타입은 ?!***

  -> 코틀린 컴파일러를 통해 Primitive 타입(기본형)으로 대체 된다. (성능 최적화)

- **Assignment in Expression : if, do-while 문 등 조건문 안에서 변수 할당이 불가능하다**
- object.wait/join() : Java에서는 object(최상단) 클래스에서 동기화 관련된 처리를 할 수 있으나, Kotlin에서는 object를 사용하지 않는다. (wait / join과 같은 동기화 관련 처리를 **코루틴**으로 처리하도록 권장)

<br>

## Immutability (불변성)

> Immutability란?!
> 한번 객체를 생성하면 바꿀 수 없는 불변 객체를 의미한다.

- `var` : mutable
- `val` : immutable (=Java의 `final`)



** **`val`로 선언해도 객체 안의 변수들은 수정이 가능하다** *즉, 객체만을 변경할 수 없다는 뜻임*

~~~kotlin
val textView = TextView(context)
textView.text = "Hello World"
textView.textSize = 15f
~~~

<br>

## Type Infercence (타입 추론)

> 대입되는 값에서 타입이 추론되면 타입을 명시하지 않아도 된다!

~~~kotlin
val PI = 3.14159 // double
val PI2 = 3.141_510f // float
val PI_Square = PI * PI2 // float (float > double)

val width = 120 // int
val height = 1_000L // long
val square = width * height // long (long > int)

val path = "path" // string

~~~

<br>

## Null Safety (널 안전성)

> 코틀린에서는 no nullable 과 nullable 두가지로 구분한다.

자료형 선언시

- String : no nullable (null 허용 X)
- String? : nullable (null 허용)

** **nullable 한 객체에 값을 접근하거나 내부 함수 사용시 널안정성을 위해 `?` , `!!` 을 이용하여 함수나 멤버를 호출한다.**

`!!` - null이 무조건 아니라고 가정하고, 함수나 멤버를 호출 ***(만약 null 이라면 NPE 발생)***

`?` - 만약 null이면  함수나 멤버를 호출하지 않고 바로 리턴해버림

** **가능한 `!!`을 사용하지 말고 `?`을 사용하자!** 

<br>

~~~kotlin
var safeName : String? = "Brown"


val nameLength1 = if (safeName != null) {
  safeName!!.toUpperCase()
} else {
  0
}


val nameLength2 = safeName?.toUpperCase()?.let {
  it.length
} // 안전호출을 하는 경우


val nameLength3 = safeName?.toUpperCase()?.lenght ?: 0
~~~

** **kotlin에는 삼항 연산자는 없고 `?:` 는 null이면 0을 리턴하는 하라 라는 의미로 사용된다. (null  체크)**

** **nullable을 되도록 사용하지 않는 것이 좋다!**

<br>

## Avoid Nullable - lateinit var , lazy 

- `lateinit var`  (`val` XXX)

  초기화 되었는지 체크를 하지 않고 사용하므로 초기화 되지 않고 사용하면 Exception 발생한다.

  ~~~kotlin
  lateinit var titleTextView : TextView
  ~~~

  

- `by lazy` - **(`val` 만 사용할 수 있음!!)**

  코트 블록을 이용하기 때문에 선언외에 다른 함수도 선언할 수 있다.

  ~~~kotlin
  val titleTextView : TextView by lazy {
  	print("findViewid")
  	activity.findViewById<TextView>(R.id.mainTitle)
  }
  
  titleTextView.text = "Welcome!!"
  ~~~



** `lateinit var` 와 `by lazy`의 차이점

`lateinit var`는 선언 후, 사용전 초기화를 하고 사용해야 함

`by lazy` 는 `titleTextView`를 호출하는 순간 `by lazy` 코드블록이 실행되어 초기화됨 (초기화하는 과정을 중간에 삽입하지 않아도 됨)

<br>

## Property

> val , var 변수에 get, set을 합쳐놓은 변수
>
> 내부의 값에 setting 할 수 있는 `keyword`를 가지고 있다.
>
> -> 함수와 변수의 속성을 모두 가지고 있는 요소

~~~kotlin
var capName : String = ""
	set(value) {
		field = value.toUpperCase()
	}
	get() = field
~~~

** **`val` 은 set() 을 사용할 수 없다 !** (주의 !)

~~~kotlin
val isMobileConnected : Boolean
	get() {
    var mobile : android.net.NetworkInfo? = null
    val manager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
    as ConnectivityManager
    	if (manager != null) {
        mobile = manager.getNetworkInfo(ConnectivityMangaer.TYPE_MOBILE)
        if(mobile != null && mobile.isConnected) return true
      } 
    return false
  }


val decorView = activity.window.decorView as FrameLayout
decorView.addView(toastView)
~~~

<br>

**Property를 사용하면 비지니스 로직 코드가 간결, 단순해짐 !** -> 변수 선언시 get,set으로 setting할 수 있기 때문에

<br>

## Code Block & Return

> 코틀린에서 `{}` 는 if-else, when, try-catch, Lambda, 등 코드블럭에서 Return을 생략할 수 있다.

~~~kotlin
val result = try {
	Uri.parse(uriString)
} catch (e : Exception) {
	null
}
~~~

exception 발생 시, null 이 될 수도 있으므로 result는 `Uri?` 가 된다.

** **코틀린에서 코드블럭에 마지막 값이 자동적으로 `return` 이 되고, 없는경우 `void`랑 같은 역할을 한다.**

