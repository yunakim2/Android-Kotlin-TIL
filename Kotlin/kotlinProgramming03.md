# 함수와 함수형 프로그래밍

## 함수 정의

```kotlin
fun 함수이름 (변수이름 : 자료형, 변수이름 : 자료형) : 리턴값 자료형 {
	 ....
	return 리턴값
}

fun sum (a : Int, b : Int) : Int {
	var sum = a+b
	return sum
}

fun sum (a : Int, b : Int) : Int  = a+b

fun sum (a : Int, b : Int) = a+b
```

<br>

### 반환값이 없는 함수

> 반환값의 자료형 `Unit`

```kotlin
fun sum (a : Int, b : Int) : Unit {
	var sum = a+b
}
fun sum (a : Int, b : Int) {
	var sum = a+b
}
```

### 매개변수 제대로 활용하기

```kotlin
fun sum (a : Int, b : Int = 3) {
	var sum = a+b
}
```

`b : Int = 3` - 매개변수의 기본값을 3으로 지정해준것

***기본값을 지정해주면 매개변수에 인자를 전달하지 않아도 실행가능***

<br>

## 가변인자 이용하기

> `vararg`

**가변인자란 ?**

인자의 개수가 변하는 것 → 함수 하나로 여러개의 인자를 받아 사용할 수 있다.

```kotlin
sum(1,2,3,4)
sum(4,5,6)

fun sum (vararg counts : Int) {
	for(num in counts) {
		println("$num")
	}
}
```

<br>

## 함수형 프로그래밍

코틀린은 함수형 프로그래밍 + 객체 지향 프로그래밍 둘다를 지원하는 다중 패러다임 언어이다.

순수 함수를 작성하여 프로그램의 부작용을 줄이는 프로그래밍 기법 → 람다식, 고차함수를 사용한다.

<br>

### 순수함수란?

부작용이 없는 함수

→ 함수 외부의 어떤 상태도 바꾸지 않는다면 순수함수라고 부른다.

→ 스레드에 사용해도 안전하고 코드를 테스트하기 쉽다.

```kotlin
fun sum (vararg counts : Int) {
	for(num in counts) {
		println("$num")
	}
}
```

**순수함수 조건**

같은 인자에 대하여 항상 같은값 반환 함수 외부의 어떤 상태도 바뀌지 않음

**순수함수가 아닌 함수**

```kotlin
fun check() {
	val test = User.grade()
	if (test != null) process(test)
}
```

→ 함수 외부에 존재하는 User객체의 함수 grade()를 사용한다.

test의 값에 따라 process는 실행되거나, 실행되지 않는다.

→ check()함수의 실행결과를 예측하기 어렵다  ***순수함수의 조건을 만족하지 못함***

<br>

### 람다식

> 함수의 이름이 없는 함수 다른 함수의 인자로 넘기는 함수 함수의 결과값으로 반환하는 함수 변수에 저장하는 함수

```kotlin
{x,y -> x+y}
```

→ 일급 객체의 특성을 가지고 있다.

### 일급 객체

- 함수의 인자로 전달할 수 있다.
- 함수의 반환값을 사용할 수 있다.
- 변수에 담을 수 있다.

→ 만약 함수가 일급 객체이면 일급함수라고 부르고, 일급 함수에 이름이 없는 경우 람다식 함수, 람다식이라고 부른다.

### 고차 함수🔥🔥🔥

> 다른 함수를 인자로 사용하거나 함수를 결과값으로 반환하는 함수

→ 일급 객체 혹은 일급 함수를 서로 주고받을 수 있는 함수

```kotlin
fun main() {
	println(highFunc({x,y -> x+y} , 10,20 ))

}

fun hightFunc(sum : (Int,Int) -> Int, a : Int, b : Int) : Int = sum(a+b)

fun hightFunc(sum : (Int,Int) -> Int, a : Int, b : Int) : Int { 
	return sum(a+b)
}
```

→ sum은 람다식 함수형식의 highFunc()의 매개변수이다.

highFunc() 는 sum()을 통해 람다식 함수를 인자로 받아들일 수 있는 고차함수이다.

<br>

### 함수형 프로그래밍 특징

1. 순수 함수를 사용해야한다.
2. 람다식을 사용할 수 있다.
3. 고차 함수를 사용할 수 있다.

<br>

## 고차함수와 람다식

#### 일반 함수를 인자나 반환값으로 사용하는 고차함수

```kotlin
fun main() {

val res1 =sum(3,2)// 일반 인자
    val res2 =mul(sum(3,3), 3)// 인자에 함수를 사용
println("res1 $res1, res2 $res2")
println("funcFunc${funcFunc()}")
}

fun sum(a : Int, b : Int)= a + b
fun mul(a : Int, b : Int)= a * b

fun funcFunc(): Int =sum(2,2)
```

<br>

#### 람다식을 인자나 반환값으로 사용하는 고차 함수

```kotlin
val multi ={x : Int, y : Int->x * y}
val  result = multi(10,20)
println("result $result")
val multi2 : (Int, Int) -> Int = {x: Int, y: Int ->
        println("x*y")
        x*y // 마지막 표현식 반환 
 }
    
val multi3 : (Int, Int) -> Int = {x:Int, y:Int -> x*y} // 생략되지 않은 전체 표현
val multi4 = {x : Int, y : Int -> x*y} // 선언 자료형 생략
val multi5 : (Int, Int) -> Int = {x , y -> x*y} // 람다식 매개변수 자료형 생략

val multi6 = {x,y -> x * y} // 오류! 추론 불가능
```

<br>

#### 반환자료형이 아예 없거나 매개변수가 하나만 있을 때

```kotlin
val greet : () -> Unit = { println("Hello World") }
val square : (Int) -> Int = {x -> x *x}

println("greet ${greet()}")
greet()
println("square : ${square(3)}")

'''
greet kotlin.Unit
Hello World
square : 9
'''
```

<br>

#### 람다식의 자료형 생략

```kotlin
val nestedLambda : () -> () -> Unit = {{ println("Hello World") }}

val greet2 = { println("Hello World") } // 추론 가능
val square2 = { x : Int -> x*x }  // x의 자료형 명시해야함!
```

<br>

#### 인자와 반환값이 없는 람다식 함수

```kotlin
val out : () -> Unit = {println("out!!")}
out()
val new = out
new()

'''
out!!
out!!
'''
```

<br>

### 람다식과 고차 함수 호출하기

#### 값에 의한 호출 ( CallByValue )

```kotlin
fun main() {
val result =callByValue(lambda())
println(result)
}

fun callByValue(b : Boolean): Boolean{
println("callByValue Function")
return b
}

vallambda:()-> Boolean ={
println("lambda Function")
true
}
```

<br>

#### 이름에 의한 람다식 호출 (CallByName)

```kotlin
fun main() {
val result =callByName(otherLambda)
println(result)
}

fun callByName(b :()->  Boolean): Boolean{
println("callByName Function")
return b()
}

valotherLambda:()-> Boolean ={
println("otherLambda Function")
true
}
```

<br>

#### 다른 함수의 참조로 인한 일반 함수 호출

```kotlin
fun main() {
// 인자와 반환값이 있는 함수
    val res1 =funcParam(3,2, ::sumNumber)
println(res1)

//인자가 없는 함수
hello(::text)

// 일반 변수에 값처럼 할당
    val likeLambda = ::sum
println(likeLambda(6,6))
}

fun sumNumber(a : Int, b : Int)= a+b
fun text(a : String, b : String)= "Hi ! $a $b"
fun funcParam(a : Int, b : Int, c :(Int , Int)-> Int)= c(a,b)

fun hello(body :(String, String)-> String): Unit{
println(body("Hello", "World"))
}
```

sum(), text()는 일반함수 임으로 이름으로 호출할 수 없다

→ `::` 을 이용하여 소괄호와 인자를 생략하고 사용가능

<br>

### 람다식의 매개변수

```kotlin
fun main() {

    // 매개변수 없는 경우
    noParam ({ " Hello World ! "})
    noParam ({ " Hello2 World ! "})

    // 매개변수 1개인 경우
    oneParam ({a -> "Hello World $a"})
    oneParam { "Hello World $it"}

    // 매개변수 여러개인 경우
    moreParam { s1, s2 ->
        "Hello World $s1 $s2"
    }

    // 일반 매개변수와 람다식 매개변수 같이 사용하기
    withArgs("Arg1", "Arg2", {a, b -> "Hello World $a $b"})

    // 2개의 람다식을 매개변수로 가진 함수
    twoLambda({a,b -> "First $a $b"}, {"Second $it"})
    

}

fun noParam (out : () -> String) = println(out())

fun oneParam ( out : (String) -> String) = println(out("OutParam"))

fun moreParam (out : (String, String) -> String) = println(out("One Param", "Two Param"))

fun withArgs (a: String, b : String, out : (String , String) -> String) = println(out(a,b))

fun twoLambda (first : (String, String) -> String , second : (String) -> String) {
    println(first("OneParam","TwoParam"))
    print(second("OneParam"))
```

<br>

### 고차함수와 람다식의 사례

#### 동기화란?

변경이 일어나면 안 되는 특정 코드를 보호하기 위한 잠금 기법 동기화로 보호되는 코드 → 임계영역

Lock을 활용하여 임계영역으로 보호함

- 자바 → Lock, ReetrantLock을 제공

```kotlin
Lock lock = new ReetrantLock()

lock.lock();

try {

	// 임계영역

} finally {

	lock.unlock();

}
```

#### 코틀린 동기화 구현

** **제네릭 `T`**

형식 매개변수, 임의의 참조 자료형 → **자료형, 클래스, 메서드 매개변수를 반환값으로 사용할 수 있다 !**

```kotlin
// 제네릭 형태의 lock
fun <T> lock(reLock : ReentrantLock, body: () -> T) : T {
    reLock.lock()
    try {
        return body()
    } finally {
        reLock.unlock()
    }
}
package chapter_03

import java.util.concurrent.locks.ReentrantLock

// 제네릭 형태의 lock
fun<T>lock(reLock : ReentrantLock, body:()-> T): T{
	reLock.lock()
try{
	return body()
    }finally{
	reLock.unlock()
    }
}

varsharable= 1

fun main() {
val reLock = ReentrantLock()
lock(reLock,{criticalFunc()})
lock(reLock){criticalFunc()}
lock(reLock, ::criticalFunc)

println(sharable)
}

fun criticalFunc() {
    sharable+= 1
}
```

