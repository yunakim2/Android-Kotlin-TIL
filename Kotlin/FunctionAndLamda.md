# Function and Lamda

## Function & Functional Type & Lamda

> 코틀린은 함수와 변수를 굳이 구별하지 않는다!

```kotlin
fun pyeong(width : Int, height : Int) : Float {
    return width * height / 3.3f
}

fun pyeong(width: Int, height: Int) = width * height / 3.3f

val pyeong = fun(width : Int, height : Int) : Float = width * height / 3.3f // 익명함수 

val pyeong : (Int, Int) -> Float = {width, height -> width * height/ 3.3f } // 람다함수

val pyeong = {width : Int, height : Int -> width * height / 3.3f } // 
```

** *함수로 정의하면 함수의 scope 안에 머물러야하지만*, 람다함수, 익명함수 등을 사용하면 **함수를 변수에 넣을 수 있어** **상속 override 가 필요 없어지고, 함수 자체를 인자로 넘길 수 있다!**

~~~kotlin
val test1 = pyeong(10,10) // 함수 fun pyeong을 실행
val test2 = pyeong.invoke(10,10) // 변수로 선언된 pyeong을 실행
~~~

** 동일한 이름의 함수와 변수명이 존재하면 함수가 우선실행됨! **만약 변수를 실행하고 싶으면 `invoke`를 이용하자**

<br>

## Currying and Named Parameter

> 함수에 파라미터 값을 기본값을 주고 정의한다.
>
> ex) 모든 파라미터를 전달할 수도 있고, 특정 파라미터만을 전달하는 것도 가능

<br>

## Lambda Expression

> Lambda Expression은 리턴을 하지 못함 
>
> Function은 리턴이 가능하다.
>
> 리턴을 하고 싶으면 익명함수로 정의하자.
>
> Lambda Expression은 짧고 간결한 것만 !

<br>

## Lambda VS Anonymous Function

- Lambda는 return 불가능
- Anonymous Function은 return 가능

<br>

## Single Abstract Method Interface🔥🔥🔥

- `SAM` 이란 ?  인터페이스 안에 구현되지 않은 인터페이스 함수가 단 1개 있는거 

  ex) `OnclickListener` 

> 코틀린에선 SAM을 Lamda를 이용하면 간단하게 인터페이스 함수를 구현할 수 있다.

~~~kotlin
view.setOnClickListener { view ->
	showToast("Button Clicked")
}
~~~

** **Java 에서 정의된 SAM 객체는 람다를 대입할 수 있으나, Kotlin에서 정의한 SAM 객체는 람다로 치환이 안된다!!**

>  Kotlin에서 정의한 SAM 객체는 `object`라는 객체 선언(Java의 new 와 같은 역할)을 이용하여 인터페이스 함수를 사용할 수 있다.

~~~kotlin
interface OnClickListener2 {
	fun onClick(view : View)
} // Kotlin에서 정의한 SAM



fun testSAM {
	setOnClickListener2 {view -> } // Error!!
  
	setOnClickListener2 (object: OnClickListener2) {
		override fun onClick(view : View) {
			// Action	
		}
	}
}
~~~

<br>

## High Order Function🔥🔥🔥

> 함수의 파라미터나 리턴에 Lambda를 받을 수 있는 것

~~~kotlin
fun filterHttps (list : List<String>, block : (List<String>) -> Unit) {
	val results = mutableListOf<String>()
  for (uri in list) {
  	if(Uri.parse(uri).scheme == "https") {
  		results += uri
  	}
  }
  block(results)
})

val list = listOf<String>("https://naver.com", "https://m.naver.com")

filterHttps(list, {result -> println(result)})

// 파라미터에 람다함수 인자가 1개인 경우는 밖으로 빼서 사용할 수 있다
filterHttps(list) { result -> println(result)}


~~~

** **주의** 🔥

> 고차함수에 파라미터 `{}` 를 밖으로 빼낼 수 있기때문에 코드를 보면 헷갈릴 수 있다
>
> 함수를 보면 `-> Unit` 처럼 마지막에 타입을 명시하기 때문에 무엇이 리턴되는 것인지 확인하고, 
>
> `{}` Lambda Expression은 리턴을 하지 않는 것을 주의하자!

<br>

## Function Extension

> Method Extension 이다. 
>
> 기존 클래스에 함수를 추가할 수 있는 것 

ex) `View`라는 클래스에 내가 원하는 함수를 추가할 수 있다.

** 코틀린에서 볼때는 추가 된 것 처럼 보이지만, 실제로 `View` 클래스에 추가 되는 것은 아님

~~~kotlin
fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}
~~~

연산자를 추가하고 싶다면 `operator`를 통에 추가해주자.

~~~kotlin
//연산자 추가
operator fun StringBuilder.plusAssign(token:Any) : Unt { this.append(token) }
val sb = StringBuilder()
sb += "This is"
sb += "KotlinWorld"

println(sb.toString()) // This is KotlinWorld
~~~



