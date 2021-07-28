# Class and Object

## Kotlin Object

- Class : 일반 클래스
- data class : 데이터를 다루는 클래스 (메소드를 넣을 수 있지만, 상속은 불가능) -> DeepCopy(Reference만 Copy하는 것이 아닌  Value를 Copy 함)
- object : static 변수로 선언됨 (=싱글톤 (Singleton))
- sealed class : Parent 클래스로 그 안에 여러개의 Child 클래스가 있는 클래스
- Enum : 열거형 클래스로, 타입에 안전한 열거값들을 구현해논 클래스



** `Singleton` : 애플리케이션 실행시 최초 한번만 메모리를 할당하고 (Static) 그 메모리에 인스턴스를 만들어 사용하는 디자인 패턴  

<br>

## Kotlin Class Feature

- 코틀린의 클래스는 `final`이 내재되어있음
- 상속을 가능하게 하려면?! `open` 키워드를 붙여야함! (`Interface` 는 `open`이 기본이다.)
- Java와 달리 **`Constructor`가 내재 선언되어 있어 초기화를 위한 `init()` 이 제공된다.**

** `class`를 `()` 로 선언시 `Constructor` 가 생략되어 있음 !

~~~kotlin
class SampleData(var index : Int, var name : String = "")
~~~

`init` 함수를 통해 초기화 단계에서 해줘야 할 것들을 작성하면 된다. *(init은 파라미터가 없다!)*

<br>

## 상속

extends와 interface 구분 없이 `:` 를 이용하여 뒤에 적어주면 된다.

~~~kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
~~~

<br>

## Data Class

> 데이터를 간결하게 담는 클래스

### 제약사항

- 반드시 primary constructor가 필요하다!
- 1개 이상의 파라미터가 반드시 존재해야하며 val/var로 정의해야한다.
- abstract, open, sealed or inner 클래스가 아니여야한다.
- interface를 확장하는 것 외에 다른 클래스 확장은 불가능하다.

### 장점

- `equals()`, `hashCode()` 쌍으로 생성이 가능

- `toString()` 생성 (=> `primary constructor`에 선언된 프로퍼티들을 예쁘게 보여준다.)

- 선언된 프로퍼티에 상응하는 `componentN()` 함수들을 생성한다. -> `dataclass`안의 값들을 한꺼번에 옮길 수 있다!

- `copy()` 생성한다. -> `primitive` 형들의 값을 `copy`하고, `class`들은 ``reference copy`를 한다.

  - 코틀린에서는 `equals` 시 데이터클래스 안의 값들을 비교하여 `value` 비교를 통해 `true`가 나온다.

    ~~~kotlin
    val moon = User("Moon",19)
    val brown = User("Brown",20)
    
    lateinit var name : String
    var age : Int = 0
    
    brown.copy(name, age)
    val unknown = User("Moon", 19)
    
    if(unknown.equals(moon) == true) { // true가 됨!
    	val(name, age) = unknown)
    }
    ~~~

<br>

## Object

> 모든 멤버가 static으로 선언되는 클래스
> singleton 객체로 생성

~~~kotlin
object AuthConstant {
    const val AUTH_PREFERENCES = "auth"
    const val TOKEN_KEY = "accessToken"
    const val REFRESH_TOKEN_KEY = "refreshToken"
    const val EMAIL_KEY = "email"
    const val IDENTIFIER_KEY = "identifier"
    const val JWTIDENTIFIER_KEY = "jwtIdentifier"
    const val AUTO_LOGIN_KEY = "auto"
    const val EXPIRE_KEY = "expire"
    const val ID = "id"
    const val TEST_JWT = "jwt"
}
~~~

 <br>

## Sealed Class 🔥🔥🔥

>  여러 개의 자식 객체를 묶어 놓은 집합 클래스

- 상속 클래스는 부모 - 자식 클래스가 하나 파일에 존재해야한다..
- `abstract`(추상 클래스) 로서 인스턴스(`new`) 화 가 불가능!
- 생성자는 `private` , `non-private`는 사용할 수 없다!

`sealed class`안에는 `data class`도 가능하고, 일반 클래스도 가능하다!

`sealed class`안 자식 클래스는 부모 클래스인  `sealed class`를 **상속을 무조건 받아야함**

~~~kotlin
sealed class NetworkResult(val token : String) {
	data class Success(val resultCode : Int) : NetworkResult("Success") // sealedClass 상속
	data class Failure(val resultCode : Int, val message : String) : NetworkResult("Failure") // sealedClass 상속
}


fun printResult(result : NetworkResult) {
  when(result){
    is NetworkResult.Success -> println("${result.token} : ${result.resultCode}")
    is NetworkResult.Failure -> println("${result.token} : ${reuslt.resultCode} - ${result.message}")
  }
}

fun fireResult() {
  val result : NetworkResult = NetworkResult.Success(200)
  printResult(result)
  printResult(NetworkResult.Failure(400, "Busy Here"))
}
~~~

<br>

## Standard Extension

> 모든 자료가 가지고 있는 확장 함수

### Null Safe 처리 및 필드 세팅시 

- `let`, `apply`, `also`, `run`, `use`
- `Run`, `with`

###  조건문  if 처리시

- `takeIf`, `takeUnless`

<br>

### <kbd>let</kbd>

> null에 대한 처리와 Sender 값을 받아 코드 블록에 전달하고, 코드 블록의 결과를 리턴함
>
> 이떄 Sender값은 `it`으로 할당

프로퍼티를 할당 후 처리하며, ?. 을 통해 null safe 처리등에 사용할 수 있다.

~~~kotlin
fun <T,R> T.let(block : (T) -> R) : R = block(this)
~~~

~~~kotlin
 Intent().let { intent -> 
	intent.data = Uri.parse("aaa:2222")
} // Intent 데이터 세팅 (new와 동일)

context?.let {
	Toast.makeText(it, "Toast", Toast.LENGTH_LONG).show()
} // null 이 아니면 it을 실행, null 이면 코드 블럭을 실행하지 않는다! null 이 아니면 Toast 함수 호출 결과인 Unit이 리턴

val uri = urlString?.let {
	Uri.parse(it)
} // 함수처럼 사용하는 방식 : urlString이 null 이 아니면 Uri.parse 실행하여 uri에 저장 
''' 결과값은 2개다 -> null 이거나 Uri.parse(it)의 결과값 '''

if(uri != null) {
	print(uri.host)
}
~~~

<br>

### <kbd>apply</kbd>

> Sender 값을 Receiver가 받아 프로퍼티나 함수를 it 없이 바로 접근하고,  Sender의 블록 결과로 리턴한다.
>
> 객체 생성 후 프로퍼티 설정시 주로 사용

~~~
fun <T> T.apply(block : T.() -> Unit) : T {
	block() ;
	return this
}
~~~

~~~kotlin
Intent().apply {
	action = Intent.ACTION_SENDTO
	data = Uri.parse("sss:2222")
	context.startActivity(this)
} // 인텐트 객체 선언과 같은 의미 = new
~~~

 -> Intent() 객체 안의 변수들을 바로 호출하여 사용할 수 있다. 





##  let, apply,run, also 사용

[참고 - 코틀린 공식 문서 (Scope Function)](https://kotlinlang.org/docs/scope-functions.html#function-selection)

- null 이 아닌 객체에서 람다를 실행할 때 -> `let`
- local 범위에서 변수 선언시 -> `let`
- 객체 생성시 -> `apply`
- 객체 생성  및 결과 계산?(실행) -> `run`
- 추가 효과 -> `also`
- 개체에 대한 함수 호출 및 그룹화 -> `with`

![스크린샷 2021-07-07 오후 8 19 07](https://media.oss.navercorp.com/user/25548/files/0c9e1d80-e026-11eb-9080-74dbb4008da7)

** let, run 은 람다의 결과를 리턴을 하고, apply, also는 리턴을 하지 않는다.

 ~~~kotlin
 val result = 20.let{it*2}.apply{toLong()*2}.run{toInt()+10}.also{it.toLong() + 10} // 50
 ~~~

-> let 코드블럭은 리턴을 하므로, 40

-> apply 코드블럭은 리턴이 없으므로, 40 그대로

-> run 코드블럭은 리턴 하므로, 50

-> also는 리턴하지 않으므로 50 

<br>

## takeIf/takeUnless/run

- `takeIf`

  조건을 만족하면 this, 아니면 null 리턴

- `run`

  독립적으로 사용시 코드 블록을 실행하고, 블록 결과 값을 돌려준다.

~~~kotlin
val path = File("sdcard/Download/test.tmp")

if(pathName.exist() == true) {
	pathName.delete()
}

File("sdcard/Download/test.tmp").takeIf(it.exist())?.delete()


//takeIf, elvis, run
val result = File("sdcard/Download/test.tmp").takeIf{it.exist()} ?: run {
	showToast("FileNotExist") 
} // 파일이 null이면 ?: 연산자에 의해 run이 실행되고 Toast를 띄운다.
~~~

<br>

## Operator overloading

> operator 를 함수 클래스 선언시 붙여 overloading 한 것처럼 사용

<br>

## Destructing 🔥🔥🔥

클래스나 집합객체의 멤버를 한꺼번에 변수로 할당하는 것

-  data class / Pair, Tuple / Collection에 정의됨
- component1(), component2() ... 등으로 정의함

** _  는 사용하지않는 것을 의미 : 받지 않아도 되는 값!

~~~kotlin
val fruit : Pair<String, Int> = "Apple" to 100
val (name, count) = fruit
val (_, onlyCount) = fruit 
~~~

